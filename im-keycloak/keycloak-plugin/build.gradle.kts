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
            exclude(project(Modules.keycloakPluginClient))
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
            Dependencies.Jvm.Keycloak.all(::compileOnly)
        }
    }
}
