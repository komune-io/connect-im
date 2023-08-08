plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(Modules.keycloakPluginDomain))
    Dependencies.Jvm.ktor(::implementation)
}
