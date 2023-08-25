plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation(project(Modules.imCommonsAuth))
    implementation(project(":im-infra:infra-redis"))

    api(project(Modules.F2.imApikeyLib))
    api(project(Modules.F2.imApikeyDomain))
    implementation(project(Modules.F2.imUserLib))
}
