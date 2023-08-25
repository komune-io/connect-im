package city.smartb.im.bdd

import city.smartb.im.commons.auth.ImRole
import io.cucumber.java8.En
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class EnvironmentCleanerSteps(
    private val context: ImTestContext,
): En {
    init {
        Before { _ ->
            context.reset()
            cleanKeycloak()
        }
    }

    private fun cleanKeycloak() = runBlocking {
        cleanKeycloakUsers()
        cleanKeycloakOrganizations()
        cleanKeycloakRoles()
    }

    private suspend fun cleanKeycloakUsers() = coroutineScope {
        context.keycloakClient().users().list().map { user ->
            async { context.keycloakClient().user(user.id).remove() }
        }.awaitAll()
    }

    private suspend fun cleanKeycloakOrganizations() = coroutineScope {
        context.keycloakClient().groups().groups().map { group ->
            async { context.keycloakClient().group(group.id).remove() }
        }.awaitAll()
    }

    private suspend fun cleanKeycloakRoles() = coroutineScope {
        val permanentRoles = ImRole.values()
            .asSequence()
            .map(ImRole::identifier)
            .plus("default-roles-${context.keycloakClient().realmId}")
            .plus("uma_authorization")
            .plus("offline_access")
            .toSet()

        context.keycloakClient().roles().list().filter { role ->
            role.name !in permanentRoles
        }.map { role ->
            async { context.keycloakClient().role(role.name).remove() }
        }.awaitAll()
    }
}
