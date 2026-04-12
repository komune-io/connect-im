plugins {
    alias(catalogue.plugins.f2.bom)
    alias(catalogue.plugins.kotlin.spring) apply false
    alias(catalogue.plugins.kotlin.serialization) apply false
    alias(catalogue.plugins.kotlin.kapt) apply false
    alias(catalogue.plugins.spring.boot) apply false

    alias(catalogue.plugins.fixers.gradle.config)
    alias(catalogue.plugins.fixers.gradle.check)

    alias(catalogue.plugins.fixers.gradle.kotlin.mpp) apply false
    alias(catalogue.plugins.fixers.gradle.kotlin.jvm) apply false
    alias(catalogue.plugins.fixers.gradle.publish)
    alias(catalogue.plugins.fixers.gradle.npm) apply false
}

fixers {
    bundle {
        id = "connect-im"
        group = "io.komune.im"
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
    repositories {
        sonatypeSnapshots = true
    }
}
