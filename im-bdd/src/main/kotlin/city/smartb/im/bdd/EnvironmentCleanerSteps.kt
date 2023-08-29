package city.smartb.im.bdd

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
        context.keycloakClient().roles().list().filter { role ->
            role.name !in context.permanentRoles()
        }.map { role ->
            async { context.keycloakClient().role(role.name).remove() }
        }.awaitAll()
    }
}
