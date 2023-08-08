package i2.keycloak.plugin.action.token

import i2.keycloak.plugin.domain.model.KeycloakPluginIds
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.services.resource.RealmResourceProviderFactory

class ActionTokenRestResourceProviderFactory: RealmResourceProviderFactory {
    override fun getId() = KeycloakPluginIds.ACTION_TOKEN
    override fun create(session: KeycloakSession) = ActionTokenRestResourceProvider(session)
    override fun init(config: Config.Scope?) { }
    override fun postInit(factory: KeycloakSessionFactory?) { }
    override fun close() { }
}
