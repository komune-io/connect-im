plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.imUserDomain))

    implementation(project(Modules.F2.imUserLib))
    implementation(project(":im-api:api-config"))
    implementation(project(Modules.imCommonsAuth))
}
