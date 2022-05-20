plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":im-commons:im-commons-domain"))
    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
    api("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
}
