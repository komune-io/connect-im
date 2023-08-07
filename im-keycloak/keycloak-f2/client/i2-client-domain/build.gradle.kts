plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":i2-keycloak:keycloak-auth:keycloak-auth-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:role:i2-role-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:user:i2-user-domain"))

    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
