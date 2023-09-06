plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.organizationDomain))

    implementation(project(Modules.Core.organizationApi))
    implementation(project(Modules.Core.privilegeApi))

    implementation(project(Modules.F2.userLib))
    implementation(project(Modules.F2.apikeyLib))

    implementation(project(Modules.imApiConfig))
    implementation(project(Modules.Infra.redis))

    implementation(project(Modules.imKeycloakF2ClientCommand))
    implementation(project(Modules.imKeycloakF2ClientQuery))
    implementation(project(Modules.imKeycloakF2ClientCommand))
    implementation(project(Modules.imKeycloakF2UserCommand))
}
