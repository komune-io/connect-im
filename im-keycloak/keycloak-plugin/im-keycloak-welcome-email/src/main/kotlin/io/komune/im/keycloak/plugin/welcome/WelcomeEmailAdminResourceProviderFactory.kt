package io.komune.im.keycloak.plugin.welcome

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.keycloak.Config
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.models.RealmModel
import org.keycloak.services.resources.admin.AdminEventBuilder
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProviderFactory
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator

class WelcomeEmailAdminResourceProviderFactory : AdminRealmResourceProviderFactory, AdminRealmResourceProvider {
    override fun getId() = KeycloakPluginIds.WELCOME_EMAIL
    override fun create(session: KeycloakSession) = this
    override fun getResource(
        session: KeycloakSession,
        realm: RealmModel,
        auth: AdminPermissionEvaluator,
        adminEvent: AdminEventBuilder
    ) = WelcomeEmailAdminResource(session = session, realm = realm, auth = auth, adminEvent = adminEvent)
    override fun init(config: Config.Scope?) { /* No-op */ }
    override fun postInit(factory: KeycloakSessionFactory?) { /* No-op */ }
    override fun close() { /* No-op */ }
}
