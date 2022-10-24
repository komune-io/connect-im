plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))
    api(project(":im-role:role-domain"))

    implementation("city.smartb.i2:i2-role-f2-add-composites:${Versions.i2}")
    implementation("city.smartb.i2:i2-role-f2-create:${Versions.i2}")
    implementation("city.smartb.i2:i2-role-f2-update:${Versions.i2}")
}
