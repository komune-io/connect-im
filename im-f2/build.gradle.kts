subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            if (!project.path.endsWith("-client") && !project.path.endsWith("-domain")) {
                implementation(project(":im-infra:im-keycloak"))
                implementation(project(":im-infra:im-redis"))
                implementation(project(":im-commons:im-commons-api"))
                implementation(project(":im-core:im-commons-core"))
            }
        }
    }

    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        dependencies {
            val commonMainApi by configurations
            commonMainApi(rootProject.catalogue.dsl.cqrs)
            commonMainApi(rootProject.catalogue.dsl.function)
        }
    }
}
