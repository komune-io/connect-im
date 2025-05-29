package io.komune.im.core.privilege.api

import f2.spring.exception.NotFoundException
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.commons.utils.matches
import io.komune.im.core.privilege.api.model.toPrivilege
import io.komune.im.core.privilege.domain.model.Privilege
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.core.privilege.domain.model.RoleModel
import io.komune.im.core.privilege.domain.model.RoleTarget
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.infra.redis.CacheName
import io.komune.im.infra.redis.CachedService
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Service
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

@Service
class PrivilegeCoreFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
): CachedService(CacheName.Privilege) {

    suspend fun getPrivilegeOrNull(identifier: PrivilegeIdentifier): Privilege? = query(identifier) {
        try {
            val client = keycloakClientProvider.getClient()
            client.role(identifier)
                .toRepresentation()
                .toPrivilege()
        } catch (e: JakartaNotFoundException) {
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
        val client = keycloakClientProvider.getClient()
        return client.roles().list(false)
            .map(RoleRepresentation::toPrivilege)
            .filter { privilege ->
                listOf(
                    privilege.type.matches(types),
                    targets == null || (privilege is RoleModel && privilege.targets.matches(targets))
                ).all { it }
            }
    }
}
