plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":im-commons:im-commons-domain"))

    commonMainApi("city.smartb.i2:i2-role-domain:${Versions.i2}")
    Dependencies.Mpp.f2(::commonMainApi)
}
