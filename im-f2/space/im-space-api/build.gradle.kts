plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:space:im-space-domain"))
    api(project(":im-f2:space:im-space-lib"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-infra:im-redis"))

    implementation(project(":im-commons:im-commons-auth"))

}
