plugins {
    id("city.smartb.fixers.gradle.kotlin.mpp")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    Dependencies.Mpp.datetime(::commonMainApi)


    Dependencies.Mpp.f2Client(::commonMainApi)
    Dependencies.Mpp.ktor(::commonMainApi)
    Dependencies.Js.ktor(::jsMainImplementation)
    Dependencies.Jvm.ktor(::jvmMainImplementation)
}
