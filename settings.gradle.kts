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
    "im-api:api-gateway",
    "im-bdd"
)

include(
    "im-commons:im-commons-api",
    "im-commons:im-commons-auth",
    "im-commons:im-commons-domain"
)

include(
    "im-infra:infra-redis",
    "im-infra:keycloak"
)

include(
    "im-core:privilege-core:privilege-core-api",
    "im-core:privilege-core:privilege-core-domain",
)

include(
    "im-f2:im-organization:organization-api",
    "im-f2:im-organization:organization-domain",
    "im-f2:im-organization:organization-client",
    "im-f2:im-organization:organization-lib",

    "im-f2:im-user:user-api",
    "im-f2:im-user:user-domain",
    "im-f2:im-user:user-client",
    "im-f2:im-user:user-lib",

    "im-f2:im-apikey:apikey-api",
    "im-f2:im-apikey:apikey-domain",
    "im-f2:im-apikey:apikey-client",
    "im-f2:im-apikey:apikey-lib",

    "im-f2:privilege:privilege-api",
    "im-f2:privilege:privilege-client",
    "im-f2:privilege:privilege-core",
    "im-f2:privilege:privilege-domain",
    "im-f2:privilege:privilege-lib"
)

include(
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
