plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(Modules.Core.userDomain))
    commonMainApi(project(Modules.F2.organizationDomain))
    commonMainApi(project(Modules.Commons.domain))
}
