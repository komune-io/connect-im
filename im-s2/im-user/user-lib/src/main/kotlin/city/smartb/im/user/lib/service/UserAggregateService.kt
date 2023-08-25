package city.smartb.im.user.lib.service

import city.smartb.f2.spring.boot.auth.AuthenticationProvider
import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileUploadCommand
import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.api.config.properties.IMProperties
import city.smartb.im.commons.exception.NotFoundException
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.orEmpty
import city.smartb.im.commons.utils.toJson
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.domain.features.command.KeycloakUserCreateCommand
import city.smartb.im.user.domain.features.command.KeycloakUserCreateFunction
import city.smartb.im.user.domain.features.command.KeycloakUserDeleteCommand
import city.smartb.im.user.domain.features.command.KeycloakUserDeleteFunction
import city.smartb.im.user.domain.features.command.KeycloakUserDisableCommand
import city.smartb.im.user.domain.features.command.KeycloakUserDisableFunction
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateCommand
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateEmailCommand
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateEmailFunction
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateFunction
import city.smartb.im.user.domain.features.command.KeycloakUserUpdatePasswordCommand
import city.smartb.im.user.domain.features.command.KeycloakUserUpdatePasswordFunction
import city.smartb.im.user.domain.features.command.UserCreateCommand
import city.smartb.im.user.domain.features.command.UserCreatedEvent
import city.smartb.im.user.domain.features.command.UserDeleteCommand
import city.smartb.im.user.domain.features.command.UserDeletedEvent
import city.smartb.im.user.domain.features.command.UserDisableCommand
import city.smartb.im.user.domain.features.command.UserDisabledEvent
import city.smartb.im.user.domain.features.command.UserResetPasswordCommand
import city.smartb.im.user.domain.features.command.UserResetPasswordEvent
import city.smartb.im.user.domain.features.command.UserUpdateCommand
import city.smartb.im.user.domain.features.command.UserUpdateEmailCommand
import city.smartb.im.user.domain.features.command.UserUpdatePasswordCommand
import city.smartb.im.user.domain.features.command.UserUpdatedEmailEvent
import city.smartb.im.user.domain.features.command.UserUpdatedEvent
import city.smartb.im.user.domain.features.command.UserUpdatedPasswordEvent
import city.smartb.im.user.domain.features.command.UserUploadLogoCommand
import city.smartb.im.user.domain.features.command.UserUploadedLogoEvent
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserId
import city.smartb.im.user.lib.config.UserFsConfig
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsCommand
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupCommand
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesSetCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesSetFunction
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesCommand
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID
import org.slf4j.LoggerFactory

@Service
class UserAggregateService(
    private val imProperties: IMProperties,
    private val authenticationResolver: ImAuthenticationProvider,
    private val keycloakUserCreateFunction: KeycloakUserCreateFunction,
    private val keycloakUserDisableFunction: KeycloakUserDisableFunction,
    private val keycloakUserDeleteFunction: KeycloakUserDeleteFunction,
    private val keycloakUserUpdateFunction: KeycloakUserUpdateFunction,
    private val keycloakUserUpdateEmailFunction: KeycloakUserUpdateEmailFunction,
    private val keycloakUserUpdatePasswordFunction: KeycloakUserUpdatePasswordFunction,
    private val userEmailSendActionsFunction: UserEmailSendActionsFunction,
    private val userFinderService: UserFinderService,
    private val userJoinGroupFunction: UserJoinGroupFunction,
    private val userRolesSetFunction: UserRolesSetFunction,
    private val userSetAttributesFunction: UserSetAttributesFunction,
    private val groupGetFunction: GroupGetFunction,
    private val redisCache: RedisCache,
) {
    private val logger = LoggerFactory.getLogger(UserAggregateService::class.java)

    @Autowired(required = false)
    private lateinit var fileClient: FileClient

    suspend fun create(command: UserCreateCommand): UserCreatedEvent {
        organizationExist(command.memberOf)

        val userId = command.toKeycloakUserCreateCommand().invokeWith(keycloakUserCreateFunction).id

        command.memberOf?.let { joinOrganization(userId, it) }

        if (command.roles.isNotEmpty()) {
            setRoles(userId, command.roles)
        }

        listOfNotNull(
            "VERIFY_EMAIL".takeIf { command.sendVerifyEmail },
            "UPDATE_PASSWORD".takeIf { command.sendResetPassword },
        ).ifEmpty { null }
            ?.let { sendEmail(userId, *it.toTypedArray()) }

        return UserCreatedEvent(userId)
    }

    suspend fun resetPassword(command: UserResetPasswordCommand): UserResetPasswordEvent {
        sendEmail(command.id, "UPDATE_PASSWORD")
        return UserResetPasswordEvent(command.id)
    }

    suspend fun updatePassword(command: UserUpdatePasswordCommand): UserUpdatedPasswordEvent {
        val auth = authenticationResolver.getAuth()
        KeycloakUserUpdatePasswordCommand(
            userId = command.id,
            password = command.password,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(keycloakUserUpdatePasswordFunction)
        return UserUpdatedPasswordEvent(command.id)
    }

    suspend fun update(command: UserUpdateCommand): UserUpdatedEvent =
        redisCache.evictIfPresent(CacheName.User, command.id) {
            organizationExist(command.memberOf)

            command.toKeycloakUserUpdateCommand().invokeWith(keycloakUserUpdateFunction)

            command.memberOf?.let { joinOrganization(command.id, it) }
            setRoles(command.id, command.roles)

            UserUpdatedEvent(command.id)
        }

    suspend fun uploadLogo(command: UserUploadLogoCommand, file: ByteArray): UserUploadedLogoEvent =
        redisCache.evictIfPresent(CacheName.User, command.id) {
            if (!::fileClient.isInitialized) {
                throw IllegalStateException("FileClient not initialized.")
            }

            val event = fileClient.fileUpload(
                command = FileUploadCommand(
                    path = UserFsConfig.pathForUser(command.id),
                ),
                file = file
            )

            val auth = authenticationResolver.getAuth()
            UserSetAttributesCommand(
                id = command.id,
                attributes = mapOf("logo" to event.url),
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(userSetAttributesFunction)

            UserUploadedLogoEvent(
                id = command.id,
                url = event.url
            )
        }

    suspend fun updateEmail(command: UserUpdateEmailCommand): UserUpdatedEmailEvent =
        redisCache.evictIfPresent(CacheName.User, command.id) {
            val auth = authenticationResolver.getAuth()
            KeycloakUserUpdateEmailCommand(
                userId = command.id,
                email = command.email,
                sendVerificationEmail = command.sendVerificationEmail,
                clientId = auth.clientId.takeUnless { auth.redirectUrl?.isBlank() ?: false },
                redirectUri = auth.redirectUrl?.ifBlank { null },
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(keycloakUserUpdateEmailFunction)

            UserUpdatedEmailEvent(
                id = command.id
            )
        }

    suspend fun disable(command: UserDisableCommand): UserDisabledEvent =
        redisCache.evictIfPresent(CacheName.User, command.id) {
            val auth = authenticationResolver.getAuth()

            val user = userFinderService.userGet(UserGetQuery(command.id))
                ?: throw NotFoundException("User", command.id)

            val event = KeycloakUserDisableCommand(
                id = command.id,
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(keycloakUserDisableFunction)

            if (command.anonymize) {
                UserUpdateCommand(
                    id = command.id,
                    givenName = "anonymous",
                    familyName = "anonymous",
                    address = (null as Address?).orEmpty(),
                    phone = "",
                    memberOf = user.memberOf?.id,
                    roles = user.roles,
                    attributes = command.attributes.orEmpty().plus(
                        listOf(
                            User::disabledBy.name to command.disabledBy.orEmpty(),
                            User::disabledDate.name to System.currentTimeMillis().toString()
                        )
                    )
                ).let { update(it) }

                UserUpdateEmailCommand(
                    id = command.id,
                    email = "${command.id}@anonymous.com",
                    sendVerificationEmail = false
                ).let { updateEmail(it) }
            }

            UserDisabledEvent(
                id = event.id
            )
        }

    suspend fun delete(command: UserDeleteCommand): UserDeletedEvent =
        redisCache.evictIfPresent(CacheName.User, command.id) {
            val auth = authenticationResolver.getAuth()

            val event = KeycloakUserDeleteCommand(
                id = command.id,
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(keycloakUserDeleteFunction)

            UserDeletedEvent(
                id = event.id
            )
        }

    private suspend fun organizationExist(organizationId: OrganizationId?) {
        if (organizationId == null) return
        val auth = authenticationResolver.getAuth()
        GroupGetQuery(
            id = organizationId,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(groupGetFunction).item
            ?: throw NotFoundException("Organization", organizationId)
    }

    private suspend fun joinOrganization(userId: UserId, organizationId: OrganizationId) {
        val auth = authenticationResolver.getAuth()
        if (organizationId.isNotBlank()) {
            UserJoinGroupCommand(
                id = userId,
                groupId = organizationId,
                realmId = auth.realmId,
                auth = auth,
                leaveOtherGroups = true
            ).invokeWith(userJoinGroupFunction)
        }
    }

    private suspend fun setRoles(userId: UserId, roles: List<String>) {
        val auth = authenticationResolver.getAuth()
        UserRolesSetCommand(
            id = userId,
            roles = roles,
            auth = auth,
            realmId = auth.realmId
        ).invokeWith(userRolesSetFunction)
    }

    private suspend fun sendEmail(userId: UserId, vararg actions: String) {
        val auth = authenticationResolver.getAuth()
            val clientId = AuthenticationProvider.getClientId().takeIf {
            imProperties.user?.action?.useJwtClientId == true
        } ?: auth.clientId.takeUnless { auth.redirectUrl?.isBlank() ?: true }
        val redirectUri = auth.redirectUrl?.ifBlank { null }
        logger.debug("sendAction[${actions.joinToString(", ")}] " +
                "realmId[${auth.realmId}] " +
                "userId[${userId}] " +
                "clientId[${clientId}] " +
                "redirectUri[${redirectUri}]"
        )
        try {
            UserEmailSendActionsCommand(
                userId = userId,
                clientId = clientId,
                redirectUri = redirectUri,
                actions = actions.toList(),
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(userEmailSendActionsFunction)
        } catch (e: Exception) {
            // TODO Send a warning to the user
            logger.error("Error sending email to user[$userId]", e)
        }

    }

    private suspend fun UserUpdateCommand.toKeycloakUserUpdateCommand(): KeycloakUserUpdateCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserUpdateCommand(
            userId = id,
            firstname = givenName,
            lastname = familyName,
            attributes = attributes.orEmpty().plus(listOfNotNull(
                address?.let { ::address.name to it.toJson() },
                phone?.let { ::phone.name to it },
                memberOf?.let { ::memberOf.name to it }
            )).toMap(),
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun UserCreateCommand.toKeycloakUserCreateCommand(): KeycloakUserCreateCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserCreateCommand(
            username = UUID.randomUUID().toString(),
            firstname = givenName,
            lastname = familyName,
            email = email,
            password = password,
            isPasswordTemporary = isPasswordTemporary,
            isEnable = true,
            isEmailVerified = isEmailVerified,
            attributes = attributes.orEmpty().plus(listOfNotNull(
                address?.let { ::address.name to it.toJson() },
                phone?.let { ::phone.name to it },
                memberOf?.let { ::memberOf.name to it }
            )).toMap(),
            realmId = auth.realmId,
            auth = auth
        )
    }
}
