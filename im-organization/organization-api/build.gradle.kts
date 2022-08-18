plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
    implementation("city.smartb.i2:group-domain:${Versions.i2}")


    api(project(":im-organization:organization-lib"))
    api(project(":im-organization:organization-domain"))
}
