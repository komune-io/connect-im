plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation(project(Modules.imCommonsAuth))
    implementation(project(":im-infra:infra-redis"))

    api(project(Modules.S2.imOrganizationDomain))
    api(project(Modules.S2.imOrganizationLib))

    implementation(project(Modules.S2.imApikeyLib))
    implementation(project(Modules.S2.imUserLib))
}
