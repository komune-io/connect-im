plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":im-keycloak:keycloak-plugin:keycloak-plugin-domain"))
    Dependencies.Jvm.ktor(::implementation)
}
