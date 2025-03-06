package io.komune.im.f2.privilege.lib

import io.komune.im.core.privilege.api.PrivilegeCoreAggregateService
import io.komune.im.core.privilege.domain.command.FeatureCoreDefineCommand
import io.komune.im.core.privilege.domain.command.PermissionCoreDefineCommand
import io.komune.im.core.privilege.domain.command.RoleCoreDefineCommand
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineCommand
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefinedEvent
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineCommand
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefinedEvent
import io.komune.im.f2.privilege.domain.role.command.RoleDefineCommand
import io.komune.im.f2.privilege.domain.role.command.RoleDefinedEvent
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
                permissions = command.permissions,
            )
        )

        return RoleDefinedEvent(event.identifier)
    }

    suspend fun define(command: PermissionDefineCommand): PermissionDefinedEvent {
        val event = privilegeCoreAggregateService.define(
            PermissionCoreDefineCommand(
                identifier = command.identifier,
                description = command.description,
                features = command.features
            )
        )

        return PermissionDefinedEvent(event.identifier)
    }

    suspend fun define(command: FeatureDefineCommand): FeatureDefinedEvent {
        val event = privilegeCoreAggregateService.define(
            FeatureCoreDefineCommand(
                identifier = command.identifier,
                description = command.description
            )
        )

        return FeatureDefinedEvent(event.identifier)
    }
}
