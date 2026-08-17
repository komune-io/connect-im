package io.komune.im.keycloak.plugin.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * These are Keycloak SPI provider ids. Keycloak resolves providers by id from realm
 * configuration and from `providers/` on disk, so changing one does not fail the build —
 * it makes an already-deployed realm stop finding its provider at runtime.
 */
class KeycloakPluginIdsTest {

    @Test
    fun `provider ids are frozen`() {
        assertThat(KeycloakPluginIds.DB_SCHEMA_MIGRATION).isEqualTo("db-schema-migration")
        assertThat(KeycloakPluginIds.ACTION_TOKEN).isEqualTo("generate-action-token")
        assertThat(KeycloakPluginIds.EVENT_WEBHOOK).isEqualTo("i2-event-http")
        assertThat(KeycloakPluginIds.MAPPER_REALM_ROLE_FEATURE)
            .isEqualTo("oidc-usermodel-realm-role-feature-mapper")
    }

    @Test
    fun `the webhook id keeps its historical i2 prefix`() {
        // Predates the rename to "im"; existing realms reference the old spelling.
        assertThat(KeycloakPluginIds.EVENT_WEBHOOK).startsWith("i2-")
    }

    @Test
    fun `ids are unique`() {
        val ids = listOf(
            KeycloakPluginIds.DB_SCHEMA_MIGRATION,
            KeycloakPluginIds.ACTION_TOKEN,
            KeycloakPluginIds.EVENT_WEBHOOK,
            KeycloakPluginIds.MAPPER_REALM_ROLE_FEATURE
        )

        assertThat(ids).doesNotHaveDuplicates()
    }
}
