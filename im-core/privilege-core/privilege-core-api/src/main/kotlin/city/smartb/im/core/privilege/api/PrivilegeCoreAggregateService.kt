package city.smartb.im.core.privilege.api

import city.smartb.im.core.privilege.api.model.toPrivilege
import city.smartb.im.core.privilege.api.model.toRoleRepresentation
import city.smartb.im.core.privilege.domain.command.PrivilegeDefineCommand
import city.smartb.im.core.privilege.domain.command.PrivilegeDefinedEvent
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.CachedService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.stereotype.Service

@Service
class PrivilegeCoreAggregateService(
    private val privilegeCoreFinderService: PrivilegeCoreFinderService,
    private val keycloakClientProvider: KeycloakClientProvider
): CachedService(CacheName.Privilege) {

    suspend fun define(command: PrivilegeDefineCommand): PrivilegeDefinedEvent = mutate(command.identifier) {
        val client = keycloakClientProvider.get()

        val oldPrivilege = privilegeCoreFinderService.getPrivilegeOrNull(command.identifier)
        val newPrivilege = command.toPrivilege(oldPrivilege?.id)

        val oldPrivilegePermissions = (oldPrivilege as? Role)?.permissions.orEmpty().toSet()
        val newPrivilegePermissions = (newPrivilege as? Role)?.permissions.orEmpty().toSet()

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

        PrivilegeDefinedEvent(
            identifier = command.identifier,
            type = command.type
        )
    }
}
