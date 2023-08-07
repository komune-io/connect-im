plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-f2:user:im-user-domain"))
    api(project(":im-keycloak:keycloak-f2:commons:im-commons-api"))
}
