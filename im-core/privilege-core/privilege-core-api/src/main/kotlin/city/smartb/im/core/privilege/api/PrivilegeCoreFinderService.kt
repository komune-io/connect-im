package city.smartb.im.core.privilege.api

import city.smartb.im.commons.model.RealmId
import city.smartb.im.core.privilege.api.model.toPrivilege
import city.smartb.im.core.privilege.domain.model.Privilege
import city.smartb.im.core.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import f2.spring.exception.NotFoundException
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
}
