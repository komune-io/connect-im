plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.imCommonsDomain))

    //    Dependencies.Jvm.f2Auth(::jvmMainImplementation)
    //TODO replace f2-spring-boot-starter-auth-tenant by  Dependencies.Jvm.f2Auth(::api)
    jvmMainImplementation(project(":im-api:f2-spring-boot-starter-auth-tenant"))

    jvmMainImplementation("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
