plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":im-keycloak:keycloak-auth:keycloak-auth-domain"))
    commonMainApi(project(":im-keycloak:keycloak-f2:role:im-role-domain"))
    commonMainApi(project(":im-keycloak:keycloak-f2:user:im-user-domain"))

    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
