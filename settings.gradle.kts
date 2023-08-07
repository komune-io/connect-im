pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "im"

include(
    "im-api:api-config",
    "im-api:api-gateway"
)

include(
    "im-bdd:bdd-commons"
)

include(
    "im-commons:im-commons-api",
    "im-commons:im-commons-auth",
    "im-commons:im-commons-domain"
)

include(
    "im-infra:infra-redis"
)

include(
    "im-organization:organization-api",
    "im-organization:organization-domain",
    "im-organization:organization-client",
    "im-organization:organization-lib"
)

include(
    "im-user:user-api",
    "im-user:user-domain",
    "im-user:user-client",
    "im-user:user-lib"
)

include(
    "im-apikey:apikey-api",
    "im-apikey:apikey-domain",
    "im-apikey:apikey-client",
    "im-apikey:apikey-lib"
)

include(
    "im-role:role-api",
    "im-role:role-domain",
    "im-role:role-client"
)

include(
    "im-keycloak:keycloak-auth:keycloak-auth-client",
    "im-keycloak:keycloak-auth:keycloak-auth-domain",
    "im-keycloak:keycloak-utils",

    "im-keycloak:keycloak-f2:commons:im-commons-api",
    "im-keycloak:keycloak-f2:commons:im-commons-domain",

    "im-keycloak:keycloak-f2:client:im-client-domain",
    "im-keycloak:keycloak-f2:client:im-client-f2-command",
    "im-keycloak:keycloak-f2:client:im-client-f2-query",

    "im-keycloak:keycloak-f2:group:im-group-domain",
    "im-keycloak:keycloak-f2:group:im-group-f2-command",
    "im-keycloak:keycloak-f2:group:im-group-f2-query",

    "im-keycloak:keycloak-f2:init:im-init-command",
    "im-keycloak:keycloak-f2:config:im-config-command",

    "im-keycloak:keycloak-f2:realm:im-realm-domain",
    "im-keycloak:keycloak-f2:realm:im-realm-f2-command",
    "im-keycloak:keycloak-f2:realm:im-realm-f2-query",

    "im-keycloak:keycloak-f2:role:im-role-domain",
    "im-keycloak:keycloak-f2:role:im-role-f2-command",
    "im-keycloak:keycloak-f2:role:im-role-f2-query",

    "im-keycloak:keycloak-f2:user:im-user-domain",
    "im-keycloak:keycloak-f2:user:im-user-f2-command",
    "im-keycloak:keycloak-f2:user:im-user-f2-query",

    "im-keycloak:keycloak-plugin",
    "im-keycloak:keycloak-plugin:keycloak-generate-action-token",
    "im-keycloak:keycloak-plugin:keycloak-event-listener-http",
    "im-keycloak:keycloak-plugin:keycloak-plugin-client",
    "im-keycloak:keycloak-plugin:keycloak-plugin-domain",

    "im-keycloak:keycloak-test:test-bdd",
    "im-keycloak:keycloak-test:test-it",

    "im-keycloak:keycloak-app:core",
    "im-keycloak:keycloak-app:init:app-init-gateway",
    "im-keycloak:keycloak-app:config:app-config-gateway"
)
