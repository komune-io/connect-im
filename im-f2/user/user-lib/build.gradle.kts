plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.userDomain))

    implementation(project(":im-api:api-config"))
    implementation(project(Modules.Infra.redis))

    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
