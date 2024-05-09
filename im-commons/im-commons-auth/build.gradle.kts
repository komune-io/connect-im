plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.Commons.domain))

    Dependencies.Jvm.f2Auth(::jvmMainImplementation)

    jvmMainImplementation("io.komune.f2:f2-dsl-function:${Versions.f2}")
    jvmMainApi("io.komune.f2:f2-spring-boot-exception-http:${Versions.f2}")
}
