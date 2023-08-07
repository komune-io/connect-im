plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-f2:role:im-role-domain"))
    api(project(":im-keycloak:keycloak-f2:commons:im-commons-api"))

}
