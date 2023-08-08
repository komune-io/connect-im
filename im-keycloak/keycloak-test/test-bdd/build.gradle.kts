plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {

    api(project(Modules.imKeycloakF2ClientQuery))
    api(project(Modules.imKeycloakF2ClientCommand))

    api(project(Modules.imKeycloakF2GroupQuery))
    api(project(Modules.imKeycloakF2GroupCommand))

    api(project(Modules.imKeycloakF2RealmQuery))
    api(project(Modules.imKeycloakF2RealmCommand))

    api(project(Modules.imKeycloakF2RoleQuery))
    api(project(Modules.imKeycloakF2RoleCommand))

    api(project(Modules.imKeycloakF2UserQuery))
    api(project(Modules.imKeycloakF2UserCommand))

    api(project(":im-keycloak:keycloak-auth:keycloak-auth-client"))

    api("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    implementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
