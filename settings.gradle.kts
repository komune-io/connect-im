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
    "im-api:f2-spring-boot-starter-auth-tenant"
)

include(
    "im-bdd"
)

include(
    "im-commons:im-commons-api",
    "im-commons:im-commons-auth",
    "im-commons:im-commons-domain"
)

include(
    "im-infra:keycloak",
    "im-infra:redis"
)

include(
    "im-core:commons-core",
    "im-core:client-core:client-core-api",
    "im-core:client-core:client-core-domain",
    "im-core:organization-core:organization-core-api",
    "im-core:organization-core:organization-core-domain",
    "im-core:privilege-core:privilege-core-api",
    "im-core:privilege-core:privilege-core-domain",
)

include(
    "im-f2:apikey:apikey-api",
    "im-f2:apikey:apikey-domain",
    "im-f2:apikey:apikey-client",
    "im-f2:apikey:apikey-lib",

    "im-f2:organization:organization-api",
    "im-f2:organization:organization-domain",
    "im-f2:organization:organization-client",
    "im-f2:organization:organization-lib",

    "im-f2:privilege:privilege-api",
    "im-f2:privilege:privilege-client",
    "im-f2:privilege:privilege-domain",
    "im-f2:privilege:privilege-lib",

    "im-f2:space:space-api",
    "im-f2:space:space-client",
    "im-f2:space:space-domain",
    "im-f2:space:space-lib",

    "im-f2:user:user-api",
    "im-f2:user:user-domain",
    "im-f2:user:user-client",
    "im-f2:user:user-lib",
)

include(
    "im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-api",
    "im-keycloak:keycloak-f2:commons:im-keycloak-f2-commons-domain",

    "im-keycloak:keycloak-f2:client:im-keycloak-f2-client-domain",
    "im-keycloak:keycloak-f2:client:im-keycloak-f2-client-command",
    "im-keycloak:keycloak-f2:client:im-keycloak-f2-client-query",

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
    "im-script:script-core",
    "im-script:script-gateway",
    "im-script:script-init",
    "im-script:script-space-config",
    "im-script:script-space-create",
)
