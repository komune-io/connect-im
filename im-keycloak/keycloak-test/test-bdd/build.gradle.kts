plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {

    implementation(project(Modules.F2.privilegeLib))

    implementation(project(Modules.imKeycloakF2ClientQuery))
    implementation(project(Modules.imKeycloakF2ClientCommand))

    implementation(project(Modules.imKeycloakF2GroupQuery))
    implementation(project(Modules.imKeycloakF2GroupCommand))

    implementation(project(Modules.imKeycloakF2RealmQuery))
    implementation(project(Modules.imKeycloakF2RealmCommand))

    implementation(project(Modules.imKeycloakF2RoleQuery))
    implementation(project(Modules.imKeycloakF2RoleCommand))

    implementation(project(Modules.imKeycloakF2UserQuery))
    implementation(project(Modules.imKeycloakF2UserCommand))

    implementation(project(Modules.Infra.keycloak))

    implementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
    implementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
}
