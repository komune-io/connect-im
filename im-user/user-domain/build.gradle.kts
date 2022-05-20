plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":im-organization:organization-domain"))
    commonMainApi(project(":im-commons:im-commons-domain"))

    commonMainApi("city.smartb.i2:user-domain:${Versions.i2}")
    Dependencies.Mpp.f2(::commonMainApi)
}
