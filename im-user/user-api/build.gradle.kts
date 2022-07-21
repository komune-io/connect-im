plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-user:user-domain"))

    implementation(project(":im-organization:organization-api"))
    implementation(project(":im-api:api-config"))
    implementation(project(":im-api:api-auth"))

    implementation("city.smartb.i2:user-f2-create:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-disable:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-email-actions:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-join-group:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-query:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-roles-set:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-set-attributes:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-update:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-update-email:${Versions.i2}")
    implementation("city.smartb.i2:user-f2-update-password:${Versions.i2}")
}
