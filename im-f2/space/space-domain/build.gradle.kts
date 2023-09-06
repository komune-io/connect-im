plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.imCommonsDomain))

    commonMainApi(project(Modules.imKeycloakF2ClientDomain))


    Dependencies.Mpp.f2(::commonMainApi)
}
