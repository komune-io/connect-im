package io.komune.im.keycloak.plugin.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * These are Keycloak SPI provider ids. Keycloak resolves providers by id from realm
 * configuration, so changing one does not fail the build — it makes an already-deployed
 * realm stop finding its provider at runtime.
 */
class KeycloakPluginIdsTest {

    @Test
    fun `provider ids are frozen`() {
        assertThat(KeycloakPluginIds.DB_SCHEMA_MIGRATION).isEqualTo("db-schema-migration")
        assertThat(KeycloakPluginIds.ACTION_TOKEN).isEqualTo("generate-action-token")
        assertThat(KeycloakPluginIds.EVENT_WEBHOOK).isEqualTo("im-event-http")
        assertThat(KeycloakPluginIds.MAPPER_REALM_ROLE_FEATURE)
            .isEqualTo("oidc-usermodel-realm-role-feature-mapper")
    }

    @Test
    fun `the pre-rename webhook id is retained so old realms keep delivering events`() {
        // A realm still naming this in eventsListeners must keep working until
        // SpaceCreateScript migrates it. Keycloak drops events for an unknown id
        // silently, so removing this constant is a silent outage, not a build failure.
        @Suppress("DEPRECATION")
        assertThat(KeycloakPluginIds.EVENT_WEBHOOK_LEGACY).isEqualTo("i2-event-http")
    }

    @Test
    fun `the current webhook id carries the im prefix`() {
        assertThat(KeycloakPluginIds.EVENT_WEBHOOK).startsWith("im-")
    }

    @Test
    fun `ids are unique`() {
        @Suppress("DEPRECATION")
        val ids = listOf(
            KeycloakPluginIds.DB_SCHEMA_MIGRATION,
            KeycloakPluginIds.ACTION_TOKEN,
            KeycloakPluginIds.EVENT_WEBHOOK,
            KeycloakPluginIds.EVENT_WEBHOOK_LEGACY,
            KeycloakPluginIds.MAPPER_REALM_ROLE_FEATURE
        )

        assertThat(ids).doesNotHaveDuplicates()
    }
}
