plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(":im-commons:im-commons-api"))
    api(project(":im-commons:im-commons-auth"))
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    Dependencies.Jvm.Fs.client(::api)
    Dependencies.Jvm.i2Auth(::api)

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
