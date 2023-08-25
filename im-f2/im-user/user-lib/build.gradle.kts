plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.imUserDomain))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-infra:infra-redis"))

    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
