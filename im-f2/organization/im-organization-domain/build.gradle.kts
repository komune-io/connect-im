plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.Core.organizationDomain))

    commonMainApi(project(Modules.F2.privilegeDomain))

    commonMainApi(project(Modules.Commons.domain))
}
