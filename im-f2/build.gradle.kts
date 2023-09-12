subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            if (!project.path.endsWith("-client")) {
                implementation(project(Modules.Infra.keycloak))
                implementation(project(Modules.Infra.redis))
                implementation(project(Modules.Commons.api))
                implementation(project(Modules.Core.commons))
            }
        }
    }

    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        dependencies {
            val commonMainApi by configurations
            Dependencies.Mpp.f2 { commonMainApi(it) }
        }
    }
}
