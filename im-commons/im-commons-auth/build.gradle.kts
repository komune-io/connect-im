plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":im-commons:im-commons-domain"))

    jvmMainImplementation("city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}")
    jvmMainImplementation("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
