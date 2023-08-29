package city.smartb.im.core.privilege.api

import city.smartb.im.commons.model.RealmId
import city.smartb.im.core.privilege.api.model.toPrivilege
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import f2.spring.exception.NotFoundException
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Service

@Service
class PrivilegeCoreFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
) {
    suspend fun getPrivilegeOrNull(realmId: RealmId?, identifier: PrivilegeIdentifier): Privilege? {
        val client = keycloakClientProvider.getFor(realmId)

        return try {
            client.role(identifier)
                .toRepresentation()
                .toPrivilege()
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun getPrivilege(realmId: RealmId?, identifier: PrivilegeIdentifier): Privilege {
        return getPrivilegeOrNull(realmId, identifier) ?: throw NotFoundException("Privilege", identifier)
    }

    suspend fun list(
        realmId: RealmId?,
        types: Collection<PrivilegeType>? = null,
        targets: Collection<RoleTarget>? = null
    ): List<Privilege> {
        val client = keycloakClientProvider.getFor(realmId)
        return client.roles().list(false)
            .map(RoleRepresentation::toPrivilege)
            .filter { privilege ->
                listOf(
                    privilege.type.matches(types),
                    targets == null || (privilege is Role && privilege.targets.matches(targets))
                ).all { it }
            }
    }

    private fun <T> T.matches(filter: Collection<T>?): Boolean {
        return filter == null || this in filter
    }

    private fun <T> Collection<T>.matches(filter: Collection<T>?): Boolean {
        return filter == null || this.any { it.matches(filter) }
    }
}
