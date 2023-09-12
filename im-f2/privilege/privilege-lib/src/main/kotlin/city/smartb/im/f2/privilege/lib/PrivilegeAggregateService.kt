package city.smartb.im.f2.privilege.lib

import city.smartb.im.core.privilege.api.PrivilegeCoreAggregateService
import city.smartb.im.core.privilege.domain.command.PermissionCoreDefineCommand
import city.smartb.im.core.privilege.domain.command.RoleCoreDefineCommand
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefineCommand
import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefinedEvent
import city.smartb.im.f2.privilege.domain.role.command.RoleDefineCommand
import city.smartb.im.f2.privilege.domain.role.command.RoleDefinedEvent
import org.springframework.stereotype.Service

@Service
class PrivilegeAggregateService(
    private val privilegeCoreAggregateService: PrivilegeCoreAggregateService,
) {
    suspend fun define(command: RoleDefineCommand): RoleDefinedEvent {
        val event = privilegeCoreAggregateService.define(
            RoleCoreDefineCommand(
                identifier = command.identifier,
                description = command.description,
                targets = command.targets.map(RoleTarget::valueOf),
                locale = command.locale,
                bindings = command.bindings?.mapKeys { (target) -> RoleTarget.valueOf(target) },
                permissions = command.permissions
            )
        )

        return RoleDefinedEvent(event.identifier)
    }

    suspend fun define(command: PermissionDefineCommand): PermissionDefinedEvent {
        val event = privilegeCoreAggregateService.define(
            PermissionCoreDefineCommand(
                identifier = command.identifier,
                description = command.description,
            )
        )

        return PermissionDefinedEvent(event.identifier)
    }
}
