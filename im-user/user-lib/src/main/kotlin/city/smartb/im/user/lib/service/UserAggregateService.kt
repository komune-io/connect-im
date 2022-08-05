package city.smartb.im.user.lib.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileUploadCommand
import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.commons.utils.toJson
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
import city.smartb.im.user.domain.model.UserId
import city.smartb.im.user.lib.config.UserFsConfig
import f2.dsl.fnc.invokeWith
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

@Service
class UserAggregateService(
    private val authenticationResolver: ImAuthenticationProvider,
    private val keycloakUserCreateFunction: KeycloakUserCreateFunction,
    private val keycloakUserDisableFunction: KeycloakUserDisableFunction,
    private val keycloakUserDeleteFunction: KeycloakUserDeleteFunction,
    private val keycloakUserUpdateFunction: KeycloakUserUpdateFunction,
    private val keycloakUserUpdateEmailFunction: KeycloakUserUpdateEmailFunction,
    private val keycloakUserUpdatePasswordFunction: KeycloakUserUpdatePasswordFunction,
    private val userEmailSendActionsFunction: UserEmailSendActionsFunction,
    private val userJoinGroupFunction: UserJoinGroupFunction,
    private val userRolesSetFunction: UserRolesSetFunction,
    private val userSetAttributesFunction: UserSetAttributesFunction
) {

    @Autowired(required = false)
    private lateinit var fileClient: FileClient

    suspend fun create(command: UserCreateCommand): UserCreatedEvent {
        val userId = command.toKeycloakUserCreateCommand().invokeWith(keycloakUserCreateFunction).id

        command.memberOf?.let { joinOrganization(userId, it) }

        if (command.roles.isNotEmpty()) {
            setRoles(userId, command.roles)
        }

        if (!command.isEmailVerified) {
            sendEmail(userId, "VERIFY_EMAIL")
        }

        if (command.sendEmailLink) {
            sendEmail(userId, "UPDATE_PASSWORD")
        }
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

    suspend fun update(command: UserUpdateCommand): UserUpdatedEvent {
        val auth = authenticationResolver.getAuth()
        command.toKeycloakUserUpdateCommand().invokeWith(keycloakUserUpdateFunction)
        command.memberOf?.let {
            UserJoinGroupCommand(
                id = command.id,
                groupId = command.memberOf!!,
                realmId = auth.realmId,
                auth = auth,
                leaveOtherGroups = true
            ).invokeWith(userJoinGroupFunction)
        }

        command.memberOf?.let { joinOrganization(command.id, it) }
        setRoles(command.id, command.roles)

        if (command.sendEmailLink) {
            sendEmail(command.id, "UPDATE_PASSWORD")
        }
        return UserUpdatedEvent(command.id)
    }

    suspend fun uploadLogo(command: UserUploadLogoCommand, file: ByteArray): UserUploadedLogoEvent {
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

        return UserUploadedLogoEvent(
            id = command.id,
            url = event.url
        )
    }

    suspend fun updateEmail(command: UserUpdateEmailCommand): UserUpdatedEmailEvent {
        val auth = authenticationResolver.getAuth()
        KeycloakUserUpdateEmailCommand(
            userId = command.id,
            email = command.email,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(keycloakUserUpdateEmailFunction)

        return UserUpdatedEmailEvent(
            id = command.id
        )
    }

    suspend fun disable(command: UserDisableCommand): UserDisabledEvent {
        val auth = authenticationResolver.getAuth()

        val event = KeycloakUserDisableCommand(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(keycloakUserDisableFunction)

        return UserDisabledEvent(
            id = event.id
        )
    }

    suspend fun delete(command: UserDeleteCommand): UserDeletedEvent {
        val auth = authenticationResolver.getAuth()

        val event = KeycloakUserDeleteCommand(
            id = command.id,
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(keycloakUserDeleteFunction)

        return UserDeletedEvent(
            id = event.id
        )
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
        UserEmailSendActionsCommand(
            userId = userId,
            clientId = null,
            redirectUri = null,
            actions = actions.toList(),
            realmId = auth.realmId,
            auth = auth
        ).invokeWith(userEmailSendActionsFunction)
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
            username = email,
            firstname = givenName,
            lastname = familyName,
            email = email,
            password = password,
            isEnable = true,
            isEmailVerified = isEmailVerified,
            attributes = attributes.orEmpty().plus(listOfNotNull(
                address?.let { ::address.name to it.toJson() },
                phone?.let { ::phone.name to it },
                ::sendEmailLink.name to sendEmailLink.toJson(),
                memberOf?.let { ::memberOf.name to it }
            )).toMap(),
            realmId = auth.realmId,
            auth = auth
        )
    }
}
