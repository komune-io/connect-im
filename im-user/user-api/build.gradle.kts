plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-user:user-domain"))

    implementation(project(":im-user:user-lib"))
    implementation(project(":im-api:api-config"))
    implementation(project(":im-commons:im-commons-auth"))
}
