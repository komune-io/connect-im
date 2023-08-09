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
    "im-s2:im-organization:organization-api",
    "im-s2:im-organization:organization-domain",
    "im-s2:im-organization:organization-client",
    "im-s2:im-organization:organization-lib"
)

include(
    "im-s2:im-user:user-api",
    "im-s2:im-user:user-domain",
    "im-s2:im-user:user-client",
    "im-s2:im-user:user-lib"
)

include(
    "im-s2:im-apikey:apikey-api",
    "im-s2:im-apikey:apikey-domain",
    "im-s2:im-apikey:apikey-client",
    "im-s2:im-apikey:apikey-lib"
)

include(
    "im-s2:im-role:role-api",
    "im-s2:im-role:role-domain",
    "im-s2:im-role:role-client"
)

include(
    "im-keycloak:keycloak-auth:keycloak-auth-client",
    "im-keycloak:keycloak-auth:keycloak-auth-domain",
    "im-keycloak:keycloak-utils",

    "im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-api",
    "im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-domain",

    "im-keycloak:keycloak-f2:client:im-keycloak-f2-client-domain",
    "im-keycloak:keycloak-f2:client:im-keycloak-f2-client-command",
    "im-keycloak:keycloak-f2:client:im-keycloak-f2-client-query",

    "im-keycloak:keycloak-f2:group:im-keycloak-f2-group-domain",
    "im-keycloak:keycloak-f2:group:im-keycloak-f2-group-command",
    "im-keycloak:keycloak-f2:group:im-keycloak-f2-group-query",

    "im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-domain",
    "im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-command",
    "im-keycloak:keycloak-f2:realm:im-keycloak-f2-realm-query",

    "im-keycloak:keycloak-f2:role:im-keycloak-f2-role-domain",
    "im-keycloak:keycloak-f2:role:im-keycloak-f2-role-command",
    "im-keycloak:keycloak-f2:role:im-keycloak-f2-role-query",

    "im-keycloak:keycloak-f2:user:im-keycloak-f2-user-domain",
    "im-keycloak:keycloak-f2:user:im-keycloak-f2-user-command",
    "im-keycloak:keycloak-f2:user:im-keycloak-f2-user-query",

    "im-keycloak:keycloak-plugin",
    "im-keycloak:keycloak-plugin:keycloak-generate-action-token",
    "im-keycloak:keycloak-plugin:keycloak-event-listener-http",
    "im-keycloak:keycloak-plugin:keycloak-plugin-client",
    "im-keycloak:keycloak-plugin:keycloak-plugin-domain",

    "im-keycloak:keycloak-test:test-bdd",
    "im-keycloak:keycloak-test:test-it"
)

include(
    "im-script:im-script-gateway-configuration",
    "im-script:im-script-gateway",

    "im-script:im-script-function-init",
    "im-script:im-script-function-config",
    "im-script:im-script-function-core",
)
