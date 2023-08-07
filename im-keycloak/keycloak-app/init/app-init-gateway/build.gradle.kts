plugins {
    id("org.springframework.boot")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
//    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(project(":i2-keycloak:keycloak-app:core"))
    implementation(project(":i2-keycloak:keycloak-f2:init:i2-init-command"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    Dependencies.Jvm.f2Http(::implementation)
    Dependencies.Jvm.slf4j(::implementation)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("smartbcity/i2-init:${this.project.version}")
}
