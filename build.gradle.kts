plugins {
    kotlin("plugin.spring") version PluginVersions.kotlin apply false
    kotlin("plugin.serialization") version PluginVersions.kotlin apply false
    kotlin("kapt") version PluginVersions.kotlin apply false
    id("org.springframework.boot") version PluginVersions.springBoot apply false

    id("io.komune.fixers.gradle.config") version PluginVersions.fixers
//    id("io.komune.fixers.gradle.check") version PluginVersions.fixers
    id("io.komune.fixers.gradle.d2") version PluginVersions.fixers

    id("io.komune.fixers.gradle.kotlin.mpp") version PluginVersions.fixers apply false
    id("io.komune.fixers.gradle.kotlin.jvm") version PluginVersions.fixers apply false
    id("io.komune.fixers.gradle.publish") version PluginVersions.fixers apply false
}

allprojects {
    group = "io.komune.im"
    version = System.getenv("VERSION") ?: "latest"

    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://s01.oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
    }
}


fixers {
    d2 {
        outputDirectory = file("storybook/d2/")
    }
    bundle {
        id = "im"
        name = "IM"
        description = "Identity Management"
        url = "https://github.com/komune-io/connect-im"
    }
    kt2Ts {
        outputDirectory = "ts/"
    }

}
