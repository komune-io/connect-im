plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation(project(":im-commons:im-commons-auth"))
    implementation(project(":im-infra:im-redis"))

    api(project(":im-f2:apikey:im-apikey-lib"))
    api(project(":im-f2:apikey:im-apikey-domain"))
    implementation(project(":im-f2:user:im-user-lib"))
}
