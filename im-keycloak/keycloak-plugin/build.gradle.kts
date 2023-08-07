import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version PluginVersions.shadowJar
}

dependencies {
    subprojects.forEach(::implementation)
}

tasks {
    shadowJar {
        archiveFileName.set("keycloak-plugin-with-dependencies.jar")
        dependencies {
            exclude(dependency("org.keycloak:.*:.*"))
            exclude(project(":i2-keycloak:keycloak-plugin:keycloak-plugin-client"))
        }
    }
}

subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        the<KotlinJvmProjectExtension>().apply {
            jvmToolchain(11)
        }
        dependencies {
            val compileOnly by configurations
            compileOnly("org.keycloak:keycloak-core:${Versions.keycloak}")
            compileOnly("org.keycloak:keycloak-server-spi:${Versions.keycloak}")
            compileOnly("org.keycloak:keycloak-server-spi-private:${Versions.keycloak}")
            compileOnly("org.keycloak:keycloak-services:${Versions.keycloak}")
            compileOnly("org.keycloak:keycloak-saml-core-public:${Versions.keycloak}")
        }
    }
}
