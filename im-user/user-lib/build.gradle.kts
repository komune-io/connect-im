plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-user:user-domain"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-infra:infra-redis"))

    implementation("city.smartb.i2:i2-user-f2-command:${Versions.i2}")
    implementation("city.smartb.i2:i2-user-f2-query:${Versions.i2}")
}
