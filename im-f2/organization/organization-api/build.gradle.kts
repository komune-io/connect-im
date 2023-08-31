plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.organizationLib))

    implementation(project(Modules.imApiConfig))
    implementation(project(Modules.imCommonsAuth))
    implementation(project(Modules.Infra.redis))

    implementation(project(Modules.F2.apikeyLib))
    implementation(project(Modules.F2.userLib))
    implementation(project(Modules.F2.privilegeLib))
}
