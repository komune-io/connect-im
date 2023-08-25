subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        dependencies {
            val implementation by configurations
            implementation(project(Modules.Infra.keycloak))
            implementation(project(Modules.imCommonsApi))
        }
    }
}
