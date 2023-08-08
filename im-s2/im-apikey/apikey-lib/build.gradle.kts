plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.S2.imApikeyDomain))

    implementation(project(":im-api:api-config"))
    implementation(project(Modules.S2.imUserLib))
    implementation(project(":im-infra:infra-redis"))


    implementation(project(Modules.imKeycloakF2ClientCommand))
    implementation(project(Modules.imKeycloakF2ClientQuery))
    implementation(project(Modules.imKeycloakF2GroupQuery))
    implementation(project(Modules.imKeycloakF2GroupCommand))
    implementation(project(Modules.imKeycloakF2UserCommand))
}
