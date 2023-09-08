plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.userDomain))

    implementation(project(Modules.F2.userLib))
    implementation(project(":im-api:api-config"))
    implementation(project(Modules.Commons.auth))
}
