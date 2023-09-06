subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            implementation(project(Modules.Infra.keycloak))
            implementation(project(Modules.Infra.redis))
            implementation(project(Modules.imCommonsApi))
            implementation(project(Modules.Core.commons))
        }
    }
}
