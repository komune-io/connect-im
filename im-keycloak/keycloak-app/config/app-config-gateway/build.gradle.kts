plugins {
    id("org.springframework.boot")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
//    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(project(":i2-keycloak:keycloak-app:core"))
    implementation(project(":i2-keycloak:keycloak-f2:config:i2-config-command"))
    Dependencies.Jvm.f2Http(::implementation)
    Dependencies.Jvm.slf4j(::implementation)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("smartbcity/i2-config:${this.project.version}")
}
