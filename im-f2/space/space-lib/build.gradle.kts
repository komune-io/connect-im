plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.S2.imSpaceDomain))

    implementation(project(Modules.imKeycloakF2RealmCommand))
    implementation(project(Modules.imKeycloakF2RealmQuery))

    implementation(project(":im-api:api-config"))
    implementation(project(Modules.Infra.redis))
}
