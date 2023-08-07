plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(Modules.imCommonsDomain))
    api("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
    Dependencies.Jvm.f2(::api)
}
