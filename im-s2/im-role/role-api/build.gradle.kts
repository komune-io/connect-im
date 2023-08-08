plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))
    api(project(Modules.S2.imRoleDomain))

    implementation(project(Modules.imKeycloakF2RoleCommand))
}
