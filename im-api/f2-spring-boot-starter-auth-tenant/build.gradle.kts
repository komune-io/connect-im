import io.komune.gradle.dependencies.Scope
import io.komune.gradle.dependencies.add

plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    security(::api)
    oauth2(::api)
    autoconfigure(::annotationProcessor)
//    Dependencies.Jvm.Spring.security(::api)
//    Dependencies.Jvm.Spring.oauth2(::api)
//    Dependencies.Jvm.Spring.autoconfigure(::implementation)
}



fun autoconfigure(scope: Scope) = scope.add(
    "org.springframework.boot:spring-boot-configuration-processor"
)

fun security(scope: Scope) = scope.add(
    "org.springframework.boot:spring-boot-starter-security:${Versions.springBoot}"
)

fun oauth2(scope: Scope) = scope.add(
    "org.springframework.security:spring-security-oauth2-resource-server:${Versions.springSecurity}",
    "org.springframework.security:spring-security-oauth2-jose:${Versions.springSecurity}"
)