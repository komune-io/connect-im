package city.smartb.im.f2.user.lib

import city.smartb.im.commons.auth.AuthenticationProvider
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.commons.utils.EmptyAddress
import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.organization.api.OrganizationCoreFinderService
import city.smartb.im.core.privilege.api.PrivilegeCoreFinderService
import city.smartb.im.core.privilege.api.model.checkTarget
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.core.user.api.UserCoreAggregateService
import city.smartb.im.core.user.domain.command.UserCoreDefineCommand
import city.smartb.im.core.user.domain.command.UserCoreDisableCommand
import city.smartb.im.core.user.domain.command.UserCoreSendEmailCommand
import city.smartb.im.f2.user.domain.command.UserCreateCommand
import city.smartb.im.f2.user.domain.command.UserCreatedEvent
import city.smartb.im.f2.user.domain.command.UserDeleteCommand
import city.smartb.im.f2.user.domain.command.UserDeletedEvent
import city.smartb.im.f2.user.domain.command.UserDisableCommand
import city.smartb.im.f2.user.domain.command.UserDisabledEvent
import city.smartb.im.f2.user.domain.command.UserResetPasswordCommand
import city.smartb.im.f2.user.domain.command.UserResetPasswordEvent
import city.smartb.im.f2.user.domain.command.UserUpdateCommand
import city.smartb.im.f2.user.domain.command.UserUpdateEmailCommand
import city.smartb.im.f2.user.domain.command.UserUpdatePasswordCommand
import city.smartb.im.f2.user.domain.command.UserUpdatedEmailEvent
import city.smartb.im.f2.user.domain.command.UserUpdatedEvent
import city.smartb.im.f2.user.domain.command.UserUpdatedPasswordEvent
import city.smartb.im.f2.user.domain.model.UserDTO
import org.keycloak.events.EventType
import org.springframework.stereotype.Service

@Service
class UserAggregateService(
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val userCoreAggregateService: UserCoreAggregateService,
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

        UserCoreDefineCommand(
            id = command.id,
            givenName = command.givenName,
            familyName = command.familyName,
            roles = command.roles,
            attributes = command.attributes.orEmpty().plus(listOfNotNull(
                command.address?.let { UserDTO::address.name to it.toJson() },
                command.phone?.let { UserDTO::phone.name to it },
            )).toMap(),
        ).let { userCoreAggregateService.define(it) }

        return UserUpdatedEvent(command.id)
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

    private suspend fun checkOrganizationExist(organizationId: OrganizationId?) {
        organizationId?.let { organizationCoreFinderService.get(it) }
    }

    private suspend fun checkRoles(roles: List<PrivilegeIdentifier>) {
        roles.mapAsync {
            val privilege = privilegeCoreFinderService.getPrivilege(it)
            privilege.checkTarget(RoleTarget.USER)
        }
    }
}
