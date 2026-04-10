plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:user:im-user-domain"))

    implementation(project(":im-f2:user:im-user-lib"))
    implementation(project(":im-core:mfa-core:im-mfa-core-api"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-commons:im-commons-auth"))
}
