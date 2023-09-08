plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.Core.userDomain))
    commonMainApi(project(Modules.F2.organizationDomain))
    commonMainApi(project(Modules.Commons.domain))
}
