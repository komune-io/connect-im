package city.smartb.im.user.api.service

import city.smartb.fs.s2.file.client.FileClient
import city.smartb.fs.s2.file.domain.features.command.FileUploadCommand
import city.smartb.im.api.auth.ImAuthenticationResolver
import city.smartb.im.commons.utils.toJson
import city.smartb.im.user.api.config.UserFsConfig
import city.smartb.im.user.domain.features.command.KeycloakUserCreateCommand
import city.smartb.im.user.domain.features.command.KeycloakUserCreateFunction
import city.smartb.im.user.domain.features.command.KeycloakUserResetPasswordCommand
import city.smartb.im.user.domain.features.command.KeycloakUserResetPasswordFunction
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateCommand
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateFunction
import city.smartb.im.user.domain.features.command.UserCreateCommand
import city.smartb.im.user.domain.features.command.UserCreatedEvent
import city.smartb.im.user.domain.features.command.UserResetPasswordCommand
import city.smartb.im.user.domain.features.command.UserResetPasswordEvent
import city.smartb.im.user.domain.features.command.UserUpdateCommand
import city.smartb.im.user.domain.features.command.UserUpdatedEvent
import city.smartb.im.user.domain.features.command.UserUploadLogoCommand
import city.smartb.im.user.domain.features.command.UserUploadedLogoEvent
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsCommand
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupCommand
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesCommand
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.springframework.stereotype.Service

@Service
class UserAggregateService(
    private val authenticationResolver: ImAuthenticationResolver,
    private val fileClient: FileClient,
    private val keycloakUserCreateFunction: KeycloakUserCreateFunction,
    private val keycloakUserResetPasswordFunction: KeycloakUserResetPasswordFunction,
    private val keycloakUserUpdateFunction: KeycloakUserUpdateFunction,
    private val userEmailSendActionsFunction: UserEmailSendActionsFunction,
    private val userJoinGroupFunction: UserJoinGroupFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
    private val userSetAttributesFunction: UserSetAttributesFunction
) {
    suspend fun userCreate(command: UserCreateCommand): UserCreatedEvent {
        val auth = authenticationResolver.getAuth()
        val userId = command.toKeycloakUserCreateCommand().invokeWith(keycloakUserCreateFunction).id
        command.memberOf?.let {
            UserJoinGroupCommand(
                id = userId,
                groupId = it,
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(userJoinGroupFunction)
        }

        UserRolesGrantCommand(
            id = userId,
            roles = command.roles,
            auth = auth,
            realmId = auth.realmId
        ).invokeWith(userRolesGrantFunction)

        if (command.sendEmailLink) {
            UserEmailSendActionsCommand(
                userId = userId,
                clientId = null,
                redirectUri = null,
                actions = listOf("UPDATE_PASSWORD"),
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(userEmailSendActionsFunction)
        }
        return UserCreatedEvent(userId)
    }

    suspend fun userResetPassword(command: UserResetPasswordCommand): UserResetPasswordEvent {
        command.toKeycloakUserResetPasswordCommand().invokeWith(keycloakUserResetPasswordFunction)
        return UserResetPasswordEvent(command.id)
    }

    suspend fun userUpdate(command: UserUpdateCommand): UserUpdatedEvent {
        val auth = authenticationResolver.getAuth()
        command.toKeycloakUserUpdateCommand().invokeWith(keycloakUserUpdateFunction)
        command.memberOf?.let {
            UserJoinGroupCommand(
                id = command.id,
                groupId = it,
                realmId = auth.realmId,
                auth = auth,
                leaveOtherGroups = true
            ).invokeWith(userJoinGroupFunction)
        }

        UserRolesGrantCommand(
            id = command.id,
            roles = command.roles,
            auth = auth,
            realmId = auth.realmId
        ).invokeWith(userRolesGrantFunction)

        if (command.sendEmailLink) {
            UserEmailSendActionsCommand(
                userId = command.id,
                clientId = null,
                redirectUri = null,
                actions = listOf("UPDATE_PASSWORD"),
                realmId = auth.realmId,
                auth = auth
            ).invokeWith(userEmailSendActionsFunction)
        }
        return UserUpdatedEvent(command.id)
    }

    suspend fun uploadLogo(command: UserUploadLogoCommand, file: ByteArray): UserUploadedLogoEvent {
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

    private suspend fun UserUpdateCommand.toKeycloakUserUpdateCommand(): KeycloakUserUpdateCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserUpdateCommand(
            userId = id,
            email = email,
            firstname = givenName,
            lastname = familyName,
            metadata = listOfNotNull(
                address?.let { ::address.name to address.toJson() },
                phone?.let { ::phone.name to it },
                memberOf?.let { ::memberOf.name to it }
            ).toMap(),
            realmId = auth.realmId,
            auth = auth
        )
    }

    private suspend fun UserResetPasswordCommand.toKeycloakUserResetPasswordCommand(): KeycloakUserResetPasswordCommand {
        val auth = authenticationResolver.getAuth()
        return KeycloakUserResetPasswordCommand(
            userId = id,
            password = password,
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
            isEnable = true,
            metadata = listOfNotNull(
                address?.let { ::address.name to address.toJson() },
                phone?.let { ::phone.name to it },
                ::sendEmailLink.name to sendEmailLink.toJson(),
                memberOf?.let { ::memberOf.name to it }
            ).toMap(),
            realmId = auth.realmId,
            auth = auth
        )
    }
}
