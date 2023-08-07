plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":i2-keycloak:keycloak-plugin:keycloak-plugin-domain"))
    Dependencies.Jvm.ktor(::implementation)
}
