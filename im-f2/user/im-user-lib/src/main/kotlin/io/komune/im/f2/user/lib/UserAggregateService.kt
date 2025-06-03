package io.komune.im.f2.user.lib

import io.komune.im.api.config.properties.IMProperties
import io.komune.im.commons.auth.AuthenticationProvider
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.utils.EmptyAddress
import io.komune.im.commons.utils.mapAsync
import io.komune.im.commons.utils.toJson
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import io.komune.im.core.privilege.api.PrivilegeCoreFinderService
import io.komune.im.core.privilege.api.model.checkTarget
import io.komune.im.core.privilege.domain.model.RoleModel
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.core.user.api.UserCoreAggregateService
import io.komune.im.core.user.domain.command.CredentialType
import io.komune.im.core.user.domain.command.UserCoreDefineCommand
import io.komune.im.core.user.domain.command.UserCoreDisableCommand
import io.komune.im.core.user.domain.command.UserCoreRemoveCredentialsCommand
import io.komune.im.core.user.domain.command.UserCoreSendEmailCommand
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.f2.user.domain.command.UserCreatedEvent
import io.komune.im.f2.user.domain.command.UserDeleteCommand
import io.komune.im.f2.user.domain.command.UserDeletedEvent
import io.komune.im.f2.user.domain.command.UserDisableCommand
import io.komune.im.f2.user.domain.command.UserDisableMfaCommand
import io.komune.im.f2.user.domain.command.UserDisabledEvent
import io.komune.im.f2.user.domain.command.UserDisabledMfavent
import io.komune.im.f2.user.domain.command.UserResetPasswordCommand
import io.komune.im.f2.user.domain.command.UserResetPasswordEvent
import io.komune.im.f2.user.domain.command.UserUpdateCommand
import io.komune.im.f2.user.domain.command.UserUpdateEmailCommand
import io.komune.im.f2.user.domain.command.UserUpdatePasswordCommand
import io.komune.im.f2.user.domain.command.UserUpdatedEmailEvent
import io.komune.im.f2.user.domain.command.UserUpdatedEvent
import io.komune.im.f2.user.domain.command.UserUpdatedPasswordEvent
import io.komune.im.f2.user.domain.model.UserDTO
import org.keycloak.events.EventType
import org.springframework.stereotype.Service

@Service
class UserAggregateService(
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val userCoreAggregateService: UserCoreAggregateService,
    private val properties: IMProperties
) {
    suspend fun create(command: UserCreateCommand): UserCreatedEvent {
        checkOrganizationExist(command.memberOf)
        checkRoles(command.roles)

        val userId = UserCoreDefineCommand(
            id = null,
            email = command.email,
            givenName = command.givenName,
            familyName = command.familyName,
            roles = command.roles,
            memberOf = command.memberOf,
            password = command.password,
            isPasswordTemporary = command.isPasswordTemporary,
            isEmailVerified = command.isEmailVerified,
            attributes = command.attributes.orEmpty().plus(listOfNotNull(
                command.address?.let { UserDTO::address.name to it.toJson() },
                command.phone?.let { UserDTO::phone.name to it },
            )).toMap(),
        ).let { userCoreAggregateService.define(it).id }

        val actions = listOfNotNull(
            EventType.VERIFY_EMAIL.takeIf { command.sendVerifyEmail },
            EventType.UPDATE_PASSWORD.takeIf { command.sendResetPassword },
        ).map(EventType::name)

        if (actions.isNotEmpty()) {
            UserCoreSendEmailCommand(
                id = userId,
                actions = actions
            ).let { userCoreAggregateService.sendEmail(it) }
        }

        return UserCreatedEvent(userId)
    }

    suspend fun resetPassword(command: UserResetPasswordCommand): UserResetPasswordEvent {
        UserCoreSendEmailCommand(
            id = command.id,
            actions = listOf(EventType.UPDATE_PASSWORD.name)
        ).let { userCoreAggregateService.sendEmail(it) }
        return UserResetPasswordEvent(command.id)
    }

    suspend fun updatePassword(command: UserUpdatePasswordCommand): UserUpdatedPasswordEvent {
        UserCoreDefineCommand(
            id = command.id,
            password = command.password,
            isPasswordTemporary = false
        ).let { userCoreAggregateService.define(it) }
        return UserUpdatedPasswordEvent(command.id)
    }

    suspend fun update(command: UserUpdateCommand): UserUpdatedEvent {
        checkRoles(command.roles)
        val event = UserCoreDefineCommand(
            id = command.id,
            givenName = command.givenName,
            familyName = command.familyName,
            roles = getValidUserRoleIdentifiers(command.memberOf, command.roles),
            memberOf = command.memberOf,
            attributes = command.attributes.orEmpty().plus(listOfNotNull(
                command.address?.let { UserDTO::address.name to it.toJson() },
                command.phone?.let { UserDTO::phone.name to it },
            )).toMap(),
        ).let { userCoreAggregateService.define(it) }

        return UserUpdatedEvent(event.id)
    }

    suspend fun updateEmail(command: UserUpdateEmailCommand): UserUpdatedEmailEvent {
        UserCoreDefineCommand(
            id = command.id,
            email = command.email,
            isEmailVerified = false
        ).let { userCoreAggregateService.define(it) }

        if (command.sendVerificationEmail) {
            UserCoreSendEmailCommand(
                id = command.id,
                actions = listOf(EventType.VERIFY_EMAIL.name)
            ).let { userCoreAggregateService.sendEmail(it) }
        }

        return UserUpdatedEmailEvent(command.id)
    }

    suspend fun disable(command: UserDisableCommand): UserDisabledEvent {
        UserCoreDisableCommand(
            id = command.id,
            disabledBy = command.disabledBy ?: AuthenticationProvider.getAuthedUser()?.id ?: ""
        ).let { userCoreAggregateService.disable(it) }

        if (command.anonymize) {
            UserCoreDefineCommand(
                id = command.id,
                email = "${command.id}@anonymous.com",
                givenName = "anonymous",
                familyName = "anonymous",
                attributes = command.attributes.orEmpty().plus(mapOf(
                    UserDTO::address.name to EmptyAddress.toJson(),
                    UserDTO::phone.name to "",
                )),
                isEmailVerified = false
            ).let { userCoreAggregateService.define(it) }
        }

        return UserDisabledEvent(command.id)
    }

    suspend fun delete(command: UserDeleteCommand): UserDeletedEvent {
        return userCoreAggregateService.delete(command)
    }


    suspend fun disableMultiFactorAuthentication(command: UserDisableMfaCommand): UserDisabledMfavent {
        userCoreAggregateService.removeCredentials(UserCoreRemoveCredentialsCommand(command.id, CredentialType.OTP))
        return UserDisabledMfavent(command.id)
    }

    private suspend fun checkOrganizationExist(organizationId: OrganizationId?) {
        organizationId?.let { organizationCoreFinderService.get(it) }
    }

    private suspend fun checkRoles(roles: List<PrivilegeIdentifier>) {
        roles.mapAsync {
            val privilege = privilegeCoreFinderService.getPrivilege(it)
            privilege.checkTarget(RoleTarget.USER)
        }
    }

    private suspend fun getValidUserRoleIdentifiers(
        organizationId: OrganizationId?,
        roles: List<RoleIdentifier>
    ): List<RoleIdentifier> {
        val organizationRoles = organizationId?.let {
            organizationCoreFinderService.get(it).roles.flatMap { roleIdentifier ->
                (privilegeCoreFinderService.getPrivilege(roleIdentifier) as RoleModel).bindings[RoleTarget.USER].orEmpty()
            }
        } ?: emptyList()
        return roles.filter { organizationRoles.contains(it) }.ifEmpty {
            properties.user?.defaultRoleIdentifiers?.split(",")?.map(String::trim) ?: emptyList()
        }
    }
}
