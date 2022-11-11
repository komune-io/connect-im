plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":im-commons:im-commons-domain"))
    api("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
    api("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
    Dependencies.Jvm.f2(::api)
}
