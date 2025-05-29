package io.komune.im.core.privilege.api

import io.komune.im.core.privilege.api.model.toPrivilege
import io.komune.im.core.privilege.api.model.toRoleRepresentation
import io.komune.im.core.privilege.domain.command.PrivilegeCoreDefineCommand
import io.komune.im.core.privilege.domain.command.PrivilegeCoreDefinedEvent
import io.komune.im.core.privilege.domain.model.RoleModel
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.infra.redis.CacheName
import io.komune.im.infra.redis.CachedService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.stereotype.Service

@Service
class PrivilegeCoreAggregateService(
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val keycloakClientProvider: KeycloakClientProvider
): CachedService(CacheName.Privilege) {

    suspend fun define(command: PrivilegeCoreDefineCommand): PrivilegeCoreDefinedEvent = mutate(command.identifier) {
        val client = keycloakClientProvider.getClient()

        val oldPrivilege = privilegeCoreFinderService.getPrivilegeOrNull(command.identifier)
        val newPrivilege = command.toPrivilege(oldPrivilege?.id)

        val oldPrivilegePermissions = (oldPrivilege as? RoleModel)?.permissions.orEmpty().toSet()
        val newPrivilegePermissions = (newPrivilege as? RoleModel)?.permissions.orEmpty().toSet()

        val removedPermissions = oldPrivilegePermissions.filter { it !in newPrivilegePermissions }
            .map { permissionIdentifier ->
                async { privilegeCoreFinderService.getPrivilegeOrNull(permissionIdentifier)?.toRoleRepresentation() }
            }.awaitAll().filterNotNull()

        val newPermissions = newPrivilegePermissions.filter { it !in oldPrivilegePermissions }
            .map { permissionIdentifier ->
                async { privilegeCoreFinderService.getPrivilege(permissionIdentifier).toRoleRepresentation() }
            }.awaitAll()

        // creation and update should be done after permissions fetch in case one of them throws 404
        if (oldPrivilege == null) {
            client.roles().create(newPrivilege.toRoleRepresentation())
        } else {
            client.role(newPrivilege.identifier).update(newPrivilege.toRoleRepresentation())
        }

        if (newPermissions.isNotEmpty()) {
            client.role(newPrivilege.identifier).addComposites(newPermissions)
        }

        if (removedPermissions.isNotEmpty()) {
            client.role(newPrivilege.identifier).deleteComposites(removedPermissions)
        }

        PrivilegeCoreDefinedEvent(
            identifier = command.identifier,
            type = command.type
        )
    }
}
