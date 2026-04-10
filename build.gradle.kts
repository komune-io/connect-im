plugins {
    alias(catalogue.plugins.f2.bom)
    alias(catalogue.plugins.kotlin.spring) apply false
    alias(catalogue.plugins.kotlin.serialization) apply false
    alias(catalogue.plugins.kotlin.kapt) apply false
    alias(catalogue.plugins.spring.boot) apply false

    alias(catalogue.plugins.fixers.gradle.config)
    alias(catalogue.plugins.fixers.gradle.check)
    id("io.komune.fixers.gradle.d2") version catalogue.versions.fixers.get()

    alias(catalogue.plugins.fixers.gradle.kotlin.mpp) apply false
    alias(catalogue.plugins.fixers.gradle.kotlin.jvm) apply false
    alias(catalogue.plugins.fixers.gradle.publish)
    id("io.komune.fixers.gradle.npm") version catalogue.versions.fixers.get() apply false
}

allprojects {
    group = "io.komune.im"
    version = System.getenv("VERSION") ?: "latest"
    repositories {
        mavenCentral()
        maven { url = uri("https://central.sonatype.com/repository/maven-snapshots") }
        if(System.getenv("MAVEN_LOCAL_USE") == "true") {
            mavenLocal()
        }
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
    npm {
        organization = "komune-io"
    }
    sonar {
        organization = "komune-io"
        projectKey = "komune-io_connect-im"
    }
}
