package city.smartb.im.bdd

import city.smartb.im.commons.utils.mapAsync
import io.cucumber.java8.En

class EnvironmentCleanerSteps: En, ImCucumberStepsDefinition() {
    init {
        Before { _ ->
            step {
                context.reset()
                withAuth(context.realmId) {
                    cleanKeycloakUsers()
                    cleanKeycloakOrganizations()
                    cleanKeycloakRoles()
                }

            }
        }
        After { _ ->
            step {
                withAuth("master") {
                    cleanKeycloakSpaces()
                }
            }
        }
    }

    private suspend fun cleanKeycloakUsers() {
        keycloakClientProvider.get().users().list().mapAsync { user ->
            keycloakClientProvider.get().user(user.id).remove()
        }
    }

    private suspend fun cleanKeycloakOrganizations() {
        keycloakClientProvider.get().groups().groups().mapAsync { group ->
            keycloakClientProvider.get().group(group.id).remove()
        }
    }

    private suspend fun cleanKeycloakRoles() {
        keycloakClientProvider.get().roles().list().filter { role ->
            role.name !in context.permanentRoles()
        }.mapAsync { role ->
            keycloakClientProvider.get().role(role.name).remove()
        }
    }

    private suspend fun cleanKeycloakSpaces() {
        context.spaceIdentifiers.items.mapAsync {
            keycloakClientProvider.get().realm(it).remove()
        }
    }
}
