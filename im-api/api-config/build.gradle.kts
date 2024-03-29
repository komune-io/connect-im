plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
    kotlin("kapt")
}

dependencies {
    api(project(Modules.Commons.api))
    api(project(Modules.Commons.auth))
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    Dependencies.Jvm.Fs.client(::api)
//    Dependencies.Jvm.i2Auth(::api)
//    Dependencies.Jvm.f2Auth(::api)
    //TODO replace f2-spring-boot-starter-auth-tenant by  Dependencies.Jvm.f2Auth(::api)
    api(project(":im-api:f2-spring-boot-starter-auth-tenant"))
}
