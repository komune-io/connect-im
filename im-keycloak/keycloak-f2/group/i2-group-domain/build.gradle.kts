plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":i2-keycloak:keycloak-f2:commons:i2-commons-domain"))
    commonMainApi(project(":i2-keycloak:keycloak-f2:role:i2-role-domain"))
    commonMainImplementation(project(":i2-keycloak:keycloak-f2:commons:i2-commons-domain"))

    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
