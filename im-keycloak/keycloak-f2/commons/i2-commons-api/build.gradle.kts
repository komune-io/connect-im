plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:commons:i2-commons-domain"))
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    Dependencies.Jvm.f2Function(::api)
    Dependencies.Jvm.slf4j(::api)
}
