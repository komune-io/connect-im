plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
    id("io.komune.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Core.organizationDomain))
    api(project(Modules.Core.mfaDomain))
    implementation(project(Modules.Infra.redis))
}
