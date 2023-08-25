plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.F2.imOrganizationDomain))
    commonMainApi(project(Modules.imCommonsDomain))

    commonMainApi(project(Modules.imKeycloakF2UserDomain))
    Dependencies.Mpp.f2(::commonMainApi)
}
