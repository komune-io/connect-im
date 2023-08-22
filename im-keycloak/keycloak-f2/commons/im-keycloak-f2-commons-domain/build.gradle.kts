plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    Dependencies.Mpp.f2(::commonMainApi)

    Dependencies.Mpp.datetime(::commonMainApi)
}
