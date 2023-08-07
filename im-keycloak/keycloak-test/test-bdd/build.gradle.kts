plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {

    api(project(":im-keycloak:keycloak-f2:client:im-client-f2-query"))
    api(project(":im-keycloak:keycloak-f2:client:im-client-f2-command"))

    api(project(":im-keycloak:keycloak-f2:group:im-group-f2-query"))
    api(project(":im-keycloak:keycloak-f2:group:im-group-f2-command"))

    api(project(":im-keycloak:keycloak-f2:realm:im-realm-f2-query"))
    api(project(":im-keycloak:keycloak-f2:realm:im-realm-f2-command"))

    api(project(":im-keycloak:keycloak-f2:role:im-role-f2-query"))
    api(project(":im-keycloak:keycloak-f2:role:im-role-f2-command"))

    api(project(":im-keycloak:keycloak-f2:user:im-user-f2-query"))
    api(project(":im-keycloak:keycloak-f2:user:im-user-f2-command"))

    api(project(":im-keycloak:keycloak-auth:keycloak-auth-client"))

    api("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    implementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
