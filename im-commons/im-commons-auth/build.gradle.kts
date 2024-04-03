plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.Commons.domain))

    //    Dependencies.Jvm.f2Auth(::jvmMainImplementation)
    //TODO replace f2-spring-boot-starter-auth-tenant by  Dependencies.Jvm.f2Auth(::api)
    jvmMainImplementation(project(":im-api:f2-spring-boot-starter-auth-tenant"))

    jvmMainImplementation("io.komune.f2:f2-dsl-function:${Versions.f2}")
    jvmMainApi("io.komune.f2:f2-spring-boot-exception-http:${Versions.f2}")
}
