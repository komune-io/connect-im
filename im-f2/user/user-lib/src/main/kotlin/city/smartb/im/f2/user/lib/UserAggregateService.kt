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
import city.smartb.im.core.user.domain.command.UserDefineCommand
import city.smartb.im.core.user.domain.command.UserDisableCommand
import city.smartb.im.core.user.domain.command.UserSendEmailCommand
import city.smartb.im.f2.user.domain.command.UserCreateCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserCreatedEventDTOBase
import city.smartb.im.f2.user.domain.command.UserDeleteCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserDeletedEventDTOBase
import city.smartb.im.f2.user.domain.command.UserDisableCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserDisabledEventDTOBase
import city.smartb.im.f2.user.domain.command.UserResetPasswordCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserResetPasswordEventDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdateCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdateEmailCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatePasswordCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatedEmailEventDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatedEventDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatedPasswordEventDTOBase
import city.smartb.im.f2.user.domain.model.UserDTO
import org.keycloak.events.EventType
import org.springframework.stereotype.Service

@Service
class UserAggregateService(
    private val organizationCoreFinderService: OrganizationCoreFinderService,
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val userCoreAggregateService: UserCoreAggregateService,
) {
    suspend fun create(command: UserCreateCommandDTOBase): UserCreatedEventDTOBase {
        checkOrganizationExist(command.memberOf)
        checkRoles(command.roles)

        val userId = UserDefineCommand(
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
            UserSendEmailCommand(
                id = userId,
                actions = actions
            ).let { userCoreAggregateService.sendEmail(it) }
        }

        return UserCreatedEventDTOBase(userId)
    }

    suspend fun resetPassword(command: UserResetPasswordCommandDTOBase): UserResetPasswordEventDTOBase {
        UserSendEmailCommand(
            id = command.id,
            actions = listOf(EventType.UPDATE_PASSWORD.name)
        ).let { userCoreAggregateService.sendEmail(it) }
        return UserResetPasswordEventDTOBase(command.id)
    }

    suspend fun updatePassword(command: UserUpdatePasswordCommandDTOBase): UserUpdatedPasswordEventDTOBase {
        UserDefineCommand(
            id = command.id,
            password = command.password,
            isPasswordTemporary = false
        ).let { userCoreAggregateService.define(it) }
        return UserUpdatedPasswordEventDTOBase(command.id)
    }

    suspend fun update(command: UserUpdateCommandDTOBase): UserUpdatedEventDTOBase {
        checkRoles(command.roles)

        UserDefineCommand(
            id = command.id,
            givenName = command.givenName,
            familyName = command.familyName,
            roles = command.roles,
            attributes = command.attributes.orEmpty().plus(listOfNotNull(
                command.address?.let { UserDTO::address.name to it.toJson() },
                command.phone?.let { UserDTO::phone.name to it },
            )).toMap(),
        ).let { userCoreAggregateService.define(it) }

        return UserUpdatedEventDTOBase(command.id)
    }

    suspend fun updateEmail(command: UserUpdateEmailCommandDTOBase): UserUpdatedEmailEventDTOBase {
        UserDefineCommand(
            id = command.id,
            email = command.email,
            isEmailVerified = false
        ).let { userCoreAggregateService.define(it) }

        if (command.sendVerificationEmail) {
            UserSendEmailCommand(
                id = command.id,
                actions = listOf(EventType.VERIFY_EMAIL.name)
            ).let { userCoreAggregateService.sendEmail(it) }
        }

        return UserUpdatedEmailEventDTOBase(command.id)
    }

    suspend fun disable(command: UserDisableCommandDTOBase): UserDisabledEventDTOBase {
        UserDisableCommand(
            id = command.id,
            disabledBy = command.disabledBy ?: AuthenticationProvider.getAuthedUser()?.id ?: ""
        ).let { userCoreAggregateService.disable(it) }

        if (command.anonymize) {
            UserDefineCommand(
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

        return UserDisabledEventDTOBase(command.id)
    }

    suspend fun delete(command: UserDeleteCommandDTOBase): UserDeletedEventDTOBase {
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
