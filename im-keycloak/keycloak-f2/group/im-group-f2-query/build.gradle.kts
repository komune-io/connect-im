plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-f2:group:im-group-domain"))
    implementation(project(":im-keycloak:keycloak-f2:role:im-role-f2-query"))
    api(project(":im-keycloak:keycloak-f2:commons:im-commons-api"))

}
