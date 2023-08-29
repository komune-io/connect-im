plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.F2.privilegeDomain))

    commonMainApi(project(Modules.imCommonsDomain))
    commonMainApi(project(Modules.imKeycloakF2ClientDomain))
    commonMainApi(project(Modules.imKeycloakF2GroupDomain))


    Dependencies.Mpp.f2(::commonMainApi)
}
