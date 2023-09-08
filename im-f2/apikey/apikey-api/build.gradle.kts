plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation(project(Modules.Commons.auth))
    implementation(project(Modules.Infra.redis))

    api(project(Modules.F2.apikeyLib))
    api(project(Modules.F2.apikeyDomain))
    implementation(project(Modules.F2.userLib))
}
