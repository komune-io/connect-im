package city.smartb.im.f2.privilege.lib

import city.smartb.im.core.privilege.api.PrivilegeCoreAggregateService
import city.smartb.im.core.privilege.domain.command.PermissionDefineCommand
import city.smartb.im.core.privilege.domain.command.RoleDefineCommand
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefineCommandDTOBase
import city.smartb.im.f2.privilege.domain.permission.command.PermissionDefinedEventDTOBase
import city.smartb.im.f2.privilege.domain.role.command.RoleDefineCommandDTOBase
import city.smartb.im.f2.privilege.domain.role.command.RoleDefinedEventDTOBase
import org.springframework.stereotype.Service

@Service
class PrivilegeAggregateService(
    private val privilegeCoreAggregateService: PrivilegeCoreAggregateService,
) {
    suspend fun define(command: RoleDefineCommandDTOBase): RoleDefinedEventDTOBase {
        val event = privilegeCoreAggregateService.define(
            RoleDefineCommand(
                realmId = command.realmId,
                identifier = command.identifier,
                description = command.description,
                targets = command.targets.map(RoleTarget::valueOf),
                locale = command.locale,
                bindings = command.bindings?.mapKeys { (target) -> RoleTarget.valueOf(target) },
                permissions = command.permissions
            )
        )

        return RoleDefinedEventDTOBase(event.identifier)
    }

    suspend fun define(command: PermissionDefineCommandDTOBase): PermissionDefinedEventDTOBase {
        val event = privilegeCoreAggregateService.define(
            PermissionDefineCommand(
                realmId = command.realmId,
                identifier = command.identifier,
                description = command.description,
            )
        )

        return PermissionDefinedEventDTOBase(event.identifier)
    }
}
