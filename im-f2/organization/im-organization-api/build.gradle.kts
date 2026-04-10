plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-f2:organization:im-organization-lib"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-commons:im-commons-auth"))
    implementation(project(":im-infra:im-redis"))

    implementation(project(":im-f2:apikey:im-apikey-lib"))
    implementation(project(":im-f2:user:im-user-lib"))
    implementation(project(":im-f2:privilege:im-privilege-lib"))
}
