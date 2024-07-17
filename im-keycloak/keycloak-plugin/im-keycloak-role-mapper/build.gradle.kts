plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(Modules.Keycloak.pluginDomain))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")
}
