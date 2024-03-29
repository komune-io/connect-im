subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            implementation(project(Modules.Infra.keycloak))
            implementation(project(Modules.Infra.redis))
            implementation(project(Modules.Commons.api))
            if (project.path != Modules.Core.commons) {
                implementation(project(Modules.Core.commons))
            }
        }
    }

    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        dependencies {
            val commonMainApi by configurations
            commonMainApi(project(Modules.Commons.domain))
            Dependencies.Mpp.f2 { commonMainApi(it) }
        }
    }
}
