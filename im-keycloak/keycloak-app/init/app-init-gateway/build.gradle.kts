plugins {
    id("org.springframework.boot")
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
    kotlin("kapt")
//    id("org.graalvm.buildtools.native")
}

dependencies {
    implementation(project(Modules.keycloakAppCore))
    implementation(project(":im-keycloak:keycloak-f2:init:im-init-command"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    Dependencies.Jvm.f2(::implementation)
    Dependencies.Jvm.slf4j(::implementation)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("smartbcity/im-init:${this.project.version}")
}
