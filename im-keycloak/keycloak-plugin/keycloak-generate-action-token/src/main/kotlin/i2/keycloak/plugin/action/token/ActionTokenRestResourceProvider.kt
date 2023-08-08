package i2.keycloak.plugin.action.token

import org.keycloak.models.KeycloakSession
import org.keycloak.services.resource.RealmResourceProvider

class ActionTokenRestResourceProvider(
    private val session: KeycloakSession
): RealmResourceProvider {
    override fun getResource() = ActionTokenRestResource(session)
    override fun close() { }
}
