plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(":i2-keycloak:keycloak-f2:config:i2-config-command"))
    Dependencies.Jvm.f2Http(::implementation)
    Dependencies.Jvm.slf4j(::implementation)
}
