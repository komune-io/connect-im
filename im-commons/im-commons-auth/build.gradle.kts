plugins {
    id("io.komune.fixers.gradle.kotlin.mpp")
    id("io.komune.fixers.gradle.publish")
}

dependencies {
    commonMainApi(project(":im-commons:im-commons-domain"))

    jvmMainImplementation(catalogue.spring.boot.starter.auth.tenant)

    jvmMainImplementation(catalogue.dsl.function)
    jvmMainApi(catalogue.spring.boot.exception.http)
}
