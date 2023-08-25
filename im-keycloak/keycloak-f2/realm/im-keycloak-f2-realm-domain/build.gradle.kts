plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.imKeycloakF2CommonsDomain))

    Dependencies.Mpp.f2(::commonMainApi)
}
