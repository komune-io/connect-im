plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    api(project(":im-commons:im-commons-domain"))
    api(project(":im-organization:organization-domain"))

//    implementation(project(":im-organization:organization-api"))
    implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}
