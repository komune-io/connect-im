package io.komune.im.bdd

import io.cucumber.java8.En
import io.komune.im.commons.utils.mapAsync
import io.komune.im.infra.keycloak.client.KeycloakClient

class EnvironmentCleanerSteps: En, ImCucumberStepsDefinition() {
    init {
        Before { _ ->
            step {
                context.reset()
                withAuth(context.realmId) {
                    val client = keycloakClientProvider.getClient()
                    client.cleanKeycloakUsers()
                    client.cleanKeycloakOrganizations()
                    client.cleanKeycloakRoles()
                    client.cleanKeycloakClients()
                }

            }
        }
        After { _ ->
            step {
                withAuth("master") {
                    val client = keycloakClientProvider.getClient()
                    client.cleanKeycloakSpaces()
                }
            }
        }
    }

    private suspend fun KeycloakClient.cleanKeycloakUsers() {
        users().list().mapAsync { user ->
            user(user.id).remove()
        }
    }

    private suspend fun KeycloakClient.cleanKeycloakOrganizations() {
        groups().groups().mapAsync { group ->
            group(group.id).remove()
        }
    }

    private suspend fun KeycloakClient.cleanKeycloakRoles() {
        roles().list().filter { role ->
            role.name !in context.permanentRoles()
        }.mapAsync { role ->
            role(role.name).remove()
        }
    }

    private suspend fun KeycloakClient.cleanKeycloakClients() {
        clients().findAll().mapAsync { clientObj ->
            if (clientObj.clientId.startsWith("tr-")) {
                client(clientObj.id).remove()
            }
        }
    }

    private suspend fun KeycloakClient.cleanKeycloakSpaces() {
        context.spaceIdentifiers.items.mapAsync {
            realm(it).remove()
        }
    }
}
