package io.komune.im.bdd

import io.cucumber.java8.En
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import s2.bdd.auth.AuthedUser
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class AuthenticationSteps: En, ImCucumberStepsDefinition() {

    init {
        DataTableType(::authenticationParams)

        Given("I am authenticated as:") { params: AuthenticationSteps.AuthenticationParams ->
            step {
                coroutineScope {
                    val userId = context.userIds.safeGet(params.identifier)
                    val client = keycloakClientProvider.getClient()
                    val userResource = client.user(userId)
                    val user = async { userResource.toRepresentation() }
                    val userRoles = async { userResource.roles().realmLevel().listEffective() }
                    context.authedUser = AuthedUser(
                        id = userId,
                        memberOf = user.await()?.attributes?.get("memberOf")?.firstOrNull(),
                        roles = userRoles.await().map { it.name }.toTypedArray()
                    )

                }
            }
        }

        Given("I am authenticated as admin") {
            step {
                val client = keycloakClientProvider.getClient(context.realmId)
                val allRoles =  client.roles().list().map { it.name }

                context.authedUser = AuthedUser(
                    id = "admin",
                    roles = allRoles.toTypedArray(),
                    memberOf = null
                )
            }
        }

        Given("I am not authenticated") {
            step {
                context.authedUser = null
            }
        }
    }

    private fun authenticationParams(entry: Map<String, String>) =
        AuthenticationParams(
            identifier = entry.safeExtract("identifier")
        )

    private data class AuthenticationParams(
        val identifier: TestContextKey
    )
}
