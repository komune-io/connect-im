package city.smartb.im.bdd

import io.cucumber.java8.En
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

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

    private suspend fun cleanKeycloakUsers() = coroutineScope {
        keycloakClientProvider.get().users().list().map { user ->
            async { keycloakClientProvider.get().user(user.id).remove() }
        }.awaitAll()
    }

    private suspend fun cleanKeycloakOrganizations() = coroutineScope {
        keycloakClientProvider.get().groups().groups().map { group ->
            async { keycloakClientProvider.get().group(group.id).remove() }
        }.awaitAll()
    }

    private suspend fun cleanKeycloakRoles() = coroutineScope {
        keycloakClientProvider.get().roles().list().filter { role ->
            role.name !in context.permanentRoles()
        }.map { role ->
            async { keycloakClientProvider.get().role(role.name).remove() }
        }.awaitAll()
    }

    private suspend fun cleanKeycloakSpaces() = coroutineScope {
        context.spaceIdentifiers.items.map {
            async { keycloakClientProvider.get().realm(it).remove() }
        }.awaitAll()
    }
}
