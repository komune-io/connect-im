package city.smartb.im.core.privilege.api

import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.commons.utils.matches
import city.smartb.im.core.privilege.api.model.toPrivilege
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeType
import city.smartb.im.core.privilege.domain.model.Role
import city.smartb.im.core.privilege.domain.model.RoleTarget
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.CachedService
import f2.spring.exception.NotFoundException
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Service

@Service
class PrivilegeCoreFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
): CachedService(CacheName.Privilege) {
    suspend fun getPrivilegeOrNull(identifier: PrivilegeIdentifier): Privilege? = query(identifier) {
        val client = keycloakClientProvider.get()

        try {
            client.role(identifier)
                .toRepresentation()
                .toPrivilege()
        } catch (e: javax.ws.rs.NotFoundException) {
            null
        }
    }

    suspend fun getPrivilege(identifier: PrivilegeIdentifier): Privilege {
        return getPrivilegeOrNull(identifier) ?: throw NotFoundException("Privilege", identifier)
    }

    suspend fun list(
        types: Collection<PrivilegeType>? = null,
        targets: Collection<RoleTarget>? = null
    ): List<Privilege> {
        val client = keycloakClientProvider.get()
        return client.roles().list(false)
            .map(RoleRepresentation::toPrivilege)
            .filter { privilege ->
                listOf(
                    privilege.type.matches(types),
                    targets == null || (privilege is Role && privilege.targets.matches(targets))
                ).all { it }
            }
    }
}
