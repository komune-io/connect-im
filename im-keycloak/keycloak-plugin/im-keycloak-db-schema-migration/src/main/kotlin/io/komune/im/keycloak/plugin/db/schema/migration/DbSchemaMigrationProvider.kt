package io.komune.im.keycloak.plugin.db.schema.migration

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider


public class DbSchemaMigrationProvider: JpaEntityProvider {

    override fun getEntities(): List<Class<*>> {
        return emptyList()
    }

    override fun getChangelogLocation(): String {
        return "META-INF/im-changelog.xml"
    }

    override fun getFactoryId(): String {
        return KeycloakPluginIds.DB_SCHEMA_MIGRATION
    }

    override fun close() {
    }



}