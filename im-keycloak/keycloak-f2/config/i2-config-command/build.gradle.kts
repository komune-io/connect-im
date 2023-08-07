plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    api(project(":i2-keycloak:keycloak-f2:client:i2-client-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:client:i2-client-f2-command"))
    implementation(project(":i2-keycloak:keycloak-f2:client:i2-client-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:realm:i2-realm-domain"))

    api(project(":i2-keycloak:keycloak-f2:role:i2-role-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:role:i2-role-f2-command"))
    implementation(project(":i2-keycloak:keycloak-f2:role:i2-role-f2-query"))

    api(project(":i2-keycloak:keycloak-f2:user:i2-user-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-command"))
    implementation(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-query"))
}
