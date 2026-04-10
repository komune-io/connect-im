subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            implementation(project(":im-infra:im-keycloak"))
            implementation(project(":im-infra:im-redis"))
            implementation(project(":im-commons:im-commons-api"))
            if (project.path != ":im-core:im-commons-core") {
                implementation(project(":im-core:im-commons-core"))
            }
        }
    }

    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        dependencies {
            val commonMainApi by configurations
            commonMainApi(project(":im-commons:im-commons-domain"))
            commonMainApi(rootProject.catalogue.dsl.cqrs)
            commonMainApi(rootProject.catalogue.dsl.function)
        }
    }
}
