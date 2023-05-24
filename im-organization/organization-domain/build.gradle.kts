plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":im-commons:im-commons-domain"))

    commonMainApi("city.smartb.i2:i2-client-domain:${Versions.i2}")
    commonMainApi("city.smartb.i2:i2-group-domain:${Versions.i2}")

    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
