plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-auth:keycloak-auth-client"))

    api(project(":im-keycloak:keycloak-f2:client:im-client-domain"))
    implementation(project(":im-keycloak:keycloak-f2:client:im-client-f2-command"))
    implementation(project(":im-keycloak:keycloak-f2:client:im-client-f2-query"))

    api(project(":im-keycloak:keycloak-f2:realm:im-realm-domain"))
    implementation(project(":im-keycloak:keycloak-f2:realm:im-realm-f2-command"))
    implementation(project(":im-keycloak:keycloak-f2:realm:im-realm-f2-query"))

    api(project(":im-keycloak:keycloak-f2:role:im-role-domain"))
    implementation(project(":im-keycloak:keycloak-f2:role:im-role-f2-command"))
    implementation(project(":im-keycloak:keycloak-f2:role:im-role-f2-query"))

    api(project(":im-keycloak:keycloak-f2:user:im-user-domain"))
    implementation(project(":im-keycloak:keycloak-f2:user:im-user-f2-command"))
    implementation(project(":im-keycloak:keycloak-f2:user:im-user-f2-query"))
}
