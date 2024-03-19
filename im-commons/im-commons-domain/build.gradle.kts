plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi("io.komune.f2:f2-dsl-cqrs:${Versions.f2}")
    Dependencies.Mpp.datetime(::commonMainApi)


    Dependencies.Mpp.f2Client(::commonMainApi)
    Dependencies.Mpp.ktor(::commonMainApi)
    Dependencies.Js.ktor(::jsMainImplementation)
    Dependencies.Jvm.ktor(::jvmMainImplementation)
}
