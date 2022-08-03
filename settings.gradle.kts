pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
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
    "im-role:role-api",
    "im-role:role-domain",
    "im-role:role-client"
)
