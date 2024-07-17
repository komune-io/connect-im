pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://s01.oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
    }
}


rootProject.name = "connect-im"

include(
    "im-api:api-config",
    "im-api:api-gateway"
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
    "im-infra:im-keycloak",
    "im-infra:im-redis"
)

include(
    "im-core:im-commons-core",

    "im-core:client-core:im-client-core-api",
    "im-core:client-core:im-client-core-domain",

    "im-core:organization-core:im-organization-core-api",
    "im-core:organization-core:im-organization-core-domain",

    "im-core:privilege-core:im-privilege-core-api",
    "im-core:privilege-core:im-privilege-core-domain",

    "im-core:user-core:im-user-core-api",
    "im-core:user-core:im-user-core-domain",
)

include(
    "im-f2:apikey:im-apikey-api",
    "im-f2:apikey:im-apikey-domain",
    "im-f2:apikey:im-apikey-client",
    "im-f2:apikey:im-apikey-lib",

    "im-f2:organization:im-organization-api",
    "im-f2:organization:im-organization-domain",
    "im-f2:organization:im-organization-client",
    "im-f2:organization:im-organization-lib",

    "im-f2:privilege:im-privilege-api",
    "im-f2:privilege:im-privilege-client",
    "im-f2:privilege:im-privilege-domain",
    "im-f2:privilege:im-privilege-lib",

    "im-f2:space:im-space-api",
    "im-f2:space:im-space-client",
    "im-f2:space:im-space-domain",
    "im-f2:space:im-space-lib",

    "im-f2:user:im-user-api",
    "im-f2:user:im-user-domain",
    "im-f2:user:im-user-client",
    "im-f2:user:im-user-lib",
)

include(
    "im-keycloak:keycloak-plugin",
    "im-keycloak:keycloak-plugin:im-keycloak-db-schema-migration",
    "im-keycloak:keycloak-plugin:im-keycloak-generate-action-token",
    "im-keycloak:keycloak-plugin:im-keycloak-event-listener-http",
    "im-keycloak:keycloak-plugin:im-keycloak-plugin-client",
    "im-keycloak:keycloak-plugin:im-keycloak-plugin-domain",
    "im-keycloak:keycloak-plugin:im-keycloak-role-mapper",
)

include(
    "im-script:im-script-core",
    "im-script:im-script-gateway",
    "im-script:im-script-init",
    "im-script:im-script-space-config",
    "im-script:im-script-space-create",
)
