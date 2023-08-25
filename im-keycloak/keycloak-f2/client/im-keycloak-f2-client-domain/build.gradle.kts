plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.imKeycloakF2RoleDomain))
    commonMainApi(project(Modules.imKeycloakF2UserDomain))

    Dependencies.Mpp.f2(::commonMainApi)
}
