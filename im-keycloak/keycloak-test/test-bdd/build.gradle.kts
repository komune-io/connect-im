plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {

    api(project(":i2-keycloak:keycloak-f2:client:i2-client-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:client:i2-client-f2-command"))

    api(project(":i2-keycloak:keycloak-f2:group:i2-group-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:group:i2-group-f2-command"))

    api(project(":i2-keycloak:keycloak-f2:realm:i2-realm-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:realm:i2-realm-f2-command"))

    api(project(":i2-keycloak:keycloak-f2:role:i2-role-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:role:i2-role-f2-command"))

    api(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-query"))
    api(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-command"))

    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    api("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    implementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
