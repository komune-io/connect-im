package city.smartb.im.user.api.service

import city.smartb.im.api.config.ImKeycloakConfig
import city.smartb.im.user.domain.features.command.KeycloakUserCreateCommand
import city.smartb.im.user.domain.features.command.KeycloakUserCreateFunction
import city.smartb.im.user.domain.features.command.KeycloakUserResetPasswordCommand
import city.smartb.im.user.domain.features.command.KeycloakUserResetPasswordFunction
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateCommand
import city.smartb.im.user.domain.features.command.KeycloakUserUpdateFunction
import city.smartb.im.user.domain.features.command.UserCreateCommand
import city.smartb.im.user.domain.features.command.UserCreateResult
import city.smartb.im.user.domain.features.command.UserResetPasswordCommand
import city.smartb.im.user.domain.features.command.UserResetPasswordResult
import city.smartb.im.user.domain.features.command.UserUpdateCommand
import city.smartb.im.user.domain.features.command.UserUpdateResult
import f2.dsl.fnc.invokeWith
import i2.commons.utils.toJson
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsCommand
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupCommand
import i2.keycloak.f2.user.domain.features.command.UserJoinGroupFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import org.springframework.stereotype.Service

@Service
class UserAggregateService(
    private val imKeycloakConfig: ImKeycloakConfig,
    private val keycloakUserCreateFunction: KeycloakUserCreateFunction,
    private val userJoinGroupFunction: UserJoinGroupFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
    private val userEmailSendActionsFunction: UserEmailSendActionsFunction,
    private val keycloakUserResetPasswordFunction: KeycloakUserResetPasswordFunction,
    private val keycloakUserUpdateFunction: KeycloakUserUpdateFunction

) {
    suspend fun userCreate(command: UserCreateCommand): UserCreateResult {
        val userId = command.toKeycloakUserCreateCommand().invokeWith(keycloakUserCreateFunction).id
        command.memberOf?.let {
            UserJoinGroupCommand(
                id = userId,
                groupId = it,
                realmId = imKeycloakConfig.realm,
                auth = imKeycloakConfig.authRealm()
            ).invokeWith(userJoinGroupFunction)
        }

        UserRolesGrantCommand(
            id = userId,
            roles = command.roles,
            auth = imKeycloakConfig.authRealm(),
            realmId = imKeycloakConfig.realm
        ).invokeWith(userRolesGrantFunction)

        if (command.sendEmailLink) {
            UserEmailSendActionsCommand(
                userId = userId,
                clientId = null,
                redirectUri = null,
                actions = listOf("UPDATE_PASSWORD"),
                realmId = imKeycloakConfig.realm,
                auth = imKeycloakConfig.authRealm()
            ).invokeWith(userEmailSendActionsFunction)
        }
        return UserCreateResult(userId)
    }

    suspend fun userResetPassword(command: UserResetPasswordCommand): UserResetPasswordResult {
        command.toKeycloakUserResetPasswordCommand().invokeWith(keycloakUserResetPasswordFunction)
        return UserResetPasswordResult(command.id)
    }

    suspend fun userUpdate(command: UserUpdateCommand): UserUpdateResult {
        command.toKeycloakUserUpdateCommand().invokeWith(keycloakUserUpdateFunction)
        command.memberOf?.let {
            UserJoinGroupCommand(
                id = command.id,
                groupId = it,
                realmId = imKeycloakConfig.realm,
                auth = imKeycloakConfig.authRealm(),
                leaveOtherGroups = true
            ).invokeWith(userJoinGroupFunction)
        }

        UserRolesGrantCommand(
            id = command.id,
            roles = command.roles,
            auth = imKeycloakConfig.authRealm(),
            realmId = imKeycloakConfig.realm
        ).invokeWith(userRolesGrantFunction)

        if (command.sendEmailLink) {
            UserEmailSendActionsCommand(
                userId = command.id,
                clientId = null,
                redirectUri = null,
                actions = listOf("UPDATE_PASSWORD"),
                realmId = imKeycloakConfig.realm,
                auth = imKeycloakConfig.authRealm()
            ).invokeWith(userEmailSendActionsFunction)
        }
        return UserUpdateResult(command.id)
    }

    private fun UserUpdateCommand.toKeycloakUserUpdateCommand() = KeycloakUserUpdateCommand(
        userId = id,
        email = email,
        firstname = givenName,
        lastname = familyName,
        metadata = listOfNotNull(
            address?.let { ::address.name to address.toJson() },
            phone?.let { ::phone.name to it },
            memberOf?.let { ::memberOf.name to it }
        ).toMap(),
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )

    private fun UserResetPasswordCommand.toKeycloakUserResetPasswordCommand() = KeycloakUserResetPasswordCommand(
        userId = id,
        password = password,
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )

    private fun UserCreateCommand.toKeycloakUserCreateCommand() = KeycloakUserCreateCommand(
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
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )
}
