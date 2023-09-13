plugins {
    kotlin("plugin.spring") version PluginVersions.kotlin apply false
    kotlin("plugin.serialization") version PluginVersions.kotlin apply false
    kotlin("kapt") version PluginVersions.kotlin apply false
    id("org.springframework.boot") version PluginVersions.springBoot apply false

    id("city.smartb.fixers.gradle.config") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.sonar") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.d2") version PluginVersions.fixers

    id("city.smartb.fixers.gradle.kotlin.mpp") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.kotlin.jvm") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.publish") version PluginVersions.fixers apply false
}

allprojects {
    group = "city.smartb.im"
    version = System.getenv("VERSION") ?: "latest"
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

//subprojects {
//    plugins.withType(city.smartb.fixers.gradle.config.ConfigPlugin::class.java).whenPluginAdded {
//        fixers {
//            bundle {
//                id = "im"
//                name = "IM"
//                description = "Identity Management"
//                url = "https://gitlab.smartb.city/framework/connect/im"
//            }
//            kt2Ts {
//                outputDirectory = "./storybook/d2"
//            }
//            d2 {
//                outputDirectory = file("storybook/d2/")
//            }
//        }
//
//    }
//}
fixers {
    d2 {
        outputDirectory = file("storybook/d2/")
    }
    bundle {
        id = "im"
        name = "IM"
        description = "Identity Management"
        url = "https://gitlab.smartb.city/framework/connect/im"
    }
    kt2Ts {
        outputDirectory = "ts/"
    }

}
