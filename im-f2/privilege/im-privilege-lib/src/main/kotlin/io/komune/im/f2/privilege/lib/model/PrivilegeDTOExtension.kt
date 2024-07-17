package io.komune.im.f2.privilege.lib.model

import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.core.privilege.domain.model.FeatureModel
import io.komune.im.core.privilege.domain.model.PermissionModel
import io.komune.im.core.privilege.domain.model.Privilege
import io.komune.im.core.privilege.domain.model.RoleModel
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.f2.privilege.domain.feature.model.Feature
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import io.komune.im.f2.privilege.domain.permission.model.Permission
import io.komune.im.f2.privilege.domain.role.model.Role
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun Privilege.toDTO(
    getRole: suspend (RoleIdentifier) -> Role
): PrivilegeDTO = when (this) {
    is PermissionModel -> toDTO()
    is RoleModel -> toDTO(getRole)
    is FeatureModel -> toDTO()
}

fun PermissionModel.toDTO() = Permission(
    id = id,
    identifier = identifier,
    description = description,
    features = features
)

suspend fun RoleModel.toDTO(
    getRole: suspend (RoleIdentifier) -> Role
) = coroutineScope {
    Role(
        id = id,
        identifier = identifier,
        description = description,
        targets = targets.map(RoleTarget::name),
        bindings = bindings.mapKeys { (target) -> target.name }
            .mapValues { (_, roles) ->
                roles.map { async {
                    if (it != identifier) { // prevents infinite loop on self-bind
                        getRole(it)
                    } else {
                        null
                    }
                } }.awaitAll().filterNotNull()
            },
        locale = locale,
        permissions = permissions,
    )
}

fun FeatureModel.toDTO() = Feature(
    id = id,
    identifier = identifier,
    description = description
)
