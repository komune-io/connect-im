plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.S2.imOrganizationLib))

    implementation(project(Modules.imApiConfig))
    implementation(project(Modules.imCommonsAuth))
    implementation(project(Modules.Infra.redis))

    implementation(project(Modules.S2.imApikeyLib))
    implementation(project(Modules.S2.imUserLib))
    implementation(project(Modules.S2.privilegeLib))
}
