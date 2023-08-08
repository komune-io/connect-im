plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.S2.imUserDomain))

    implementation(project(Modules.S2.imUserLib))
    implementation(project(":im-api:api-config"))
    implementation(project(":im-commons:im-commons-auth"))
}
