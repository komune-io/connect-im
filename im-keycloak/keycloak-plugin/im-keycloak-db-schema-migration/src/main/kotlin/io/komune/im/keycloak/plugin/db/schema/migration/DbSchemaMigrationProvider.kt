package io.komune.im.keycloak.plugin.db.schema.migration

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider

class DbSchemaMigrationProvider: JpaEntityProvider {

    override fun getEntities(): List<Class<*>> = emptyList()

    override fun getChangelogLocation(): String = "META-INF/im-changelog.xml"

    override fun getFactoryId(): String = KeycloakPluginIds.DB_SCHEMA_MIGRATION

    override fun close() {
        // No-op: nothing to clean up
    }
}
