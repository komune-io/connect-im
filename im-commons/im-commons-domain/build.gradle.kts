plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.i2:keycloak-auth-domain:${Versions.i2}")
    commonMainApi("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kdatetime}")


    Dependencies.Mpp.ktor(::commonMainApi)
    Dependencies.Js.ktor(::jsMainImplementation)
    Dependencies.Jvm.ktor(::jvmMainImplementation)
}
