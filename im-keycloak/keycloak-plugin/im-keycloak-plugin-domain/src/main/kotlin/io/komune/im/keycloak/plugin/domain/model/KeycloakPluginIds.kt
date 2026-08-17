package io.komune.im.keycloak.plugin.domain.model

object KeycloakPluginIds {
    const val DB_SCHEMA_MIGRATION = "db-schema-migration"
    const val ACTION_TOKEN = "generate-action-token"
    const val EVENT_WEBHOOK = "im-event-http"
    const val MAPPER_REALM_ROLE_FEATURE = "oidc-usermodel-realm-role-feature-mapper"

    /**
     * Previous id of the event webhook listener, from before the i2 -> im rename.
     *
     * Realms provisioned by an older release still name this in `eventsListeners`, and
     * Keycloak resolves listeners by id at event time. The listener is therefore still
     * registered under this id so those realms keep delivering events until
     * `SpaceCreateScript` migrates them to [EVENT_WEBHOOK].
     */
    @Deprecated("Provisioned realms are migrated to EVENT_WEBHOOK; kept so old realms keep working.")
    const val EVENT_WEBHOOK_LEGACY = "i2-event-http"
}
