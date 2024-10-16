plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Infra.keycloak))

    api(project(Modules.Commons.api))

    api(project(Modules.Script.core))

    implementation(project(Modules.Core.clientApi))

    implementation(project(Modules.F2.privilegeLib))
    implementation(project(Modules.F2.spaceLib))
    implementation(project(Modules.F2.userLib))

    implementation(project(Modules.Keycloak.pluginDomain))

    Dependencies.Jvm.junit(::testImplementation)
    testImplementation(project(Modules.Bdd.base))
    testImplementation(project(Modules.F2.apikeyLib))
}
