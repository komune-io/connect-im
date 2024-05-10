package io.komune.im.keycloak.plugin.db.schema.migration

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.keycloak.Config
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory

class DbSchemaMigrationProviderFactory: JpaEntityProviderFactory {

    override fun create(p0: KeycloakSession?): JpaEntityProvider {
        return DbSchemaMigrationProvider()
    }

    override fun init(config: Config.Scope) {}
    override fun postInit(factory: KeycloakSessionFactory) {}
    override fun getId(): String = KeycloakPluginIds.DB_SCHEMA_MIGRATION
    override fun close() {}
}
