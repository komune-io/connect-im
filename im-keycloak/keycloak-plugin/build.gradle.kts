import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm")
    alias(libs.plugins.shadow)
}

dependencies {
    subprojects.forEach(::implementation)
}

tasks {
    shadowJar {
        archiveFileName.set("keycloak-plugin-with-dependencies.jar")
        dependencies {
            exclude(dependency("org.keycloak:.*:.*"))
            exclude(project(":im-keycloak:keycloak-plugin:im-keycloak-plugin-client"))
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
            compileOnly(libs.bundles.keycloak.all)
        }
    }
}
