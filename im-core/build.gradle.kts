subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            implementation(project(Modules.Infra.keycloak))
            implementation(project(Modules.Infra.redis))
            implementation(project(Modules.imCommonsApi))
        }
    }

    plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
        dependencies {
            val commonMainApi by configurations
            commonMainApi(project(Modules.imCommonsDomain))
            Dependencies.Mpp.f2 { commonMainApi(it) }
        }
    }
}
