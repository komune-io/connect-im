plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation(project(":im-commons:im-commons-auth"))
    implementation(project(":im-infra:infra-redis"))

    api(project(":im-organization:organization-lib"))
    api(project(":im-organization:organization-domain"))
    implementation(project(":im-user:user-lib"))
}
