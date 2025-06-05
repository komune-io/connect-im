package io.komune.im.bdd.core.user.command

import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.user.data.user
import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.f2.user.api.UserEndpoint
import io.komune.im.f2.user.domain.command.UserUpdateCommand
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extractList

class UserUpdateSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserUpdateCommand

    init {
        DataTableType(::userUpdateParams)

        When("I update a user") {
            step {
                updateUser(userUpdateParams(null))
            }
        }

        When("I update a user:") { params: UserUpdateParams ->
            step {
                updateUser(params)
            }
        }

        Given("A user is updated") {
            step {
                updateUser(userUpdateParams(null))
            }
        }

        Given("A user is updated:") { params: UserUpdateParams ->
            step {
                updateUser(params)
            }
        }

        Given("Some users are updated:") { dataTable: DataTable ->
            step {
                dataTable.asList(UserUpdateParams::class.java)
                    .forEach { updateUser(it) }
            }
        }

        Then("The user should be updated") {
            step {
                val userId = context.userIds.lastUsed

                val client = keycloakClientProvider.getClient()
                AssertionBdd.user(client).assertThatId(userId).hasFields(
                    givenName = command.givenName,
                    familyName = command.familyName,
                    address = command.address,
                    phone = command.phone,
                    roles = command.roles,
                    attributes = command.attributes.orEmpty(),
                )
            }
        }

        Then("The user should be updated:") { dataTable: DataTable ->
            step {
                val userId = context.userIds.lastUsed
                val client = keycloakClientProvider.getClient()
                dataTable.asList(UserUpdateParams::class.java)
                    .forEach {
                        AssertionBdd.user(client).assertThatId(userId).hasFields(
                            givenName = it.givenName,
                            familyName = it.familyName,
                            address = it.address,
                            phone = it.phone,
                            roles = it.roles,
                            attributes = it.attributes,
                        )
                    }
            }
        }

        Then("The user roles should be:") { params: UserUpdateParams ->
            step {
                val userId = context.userIds.lastUsed
                val client = keycloakClientProvider.getClient()

                AssertionBdd.user(client).assertThatId(userId).hasFields(
                    roles = params.roles,
                )
            }
        }
    }

    private suspend fun updateUser(params: UserUpdateParams) {
        command = UserUpdateCommand(
            id = context.userIds.safeGet(params.identifier),
            givenName = params.givenName,
            familyName = params.familyName,
            address = params.address,
            phone = params.phone,
            roles = params.roles,
            memberOf = params.memberOf?.let { context.organizationIds[it] ?: it },
            attributes = params.attributes,
        )

        userEndpoint.userUpdate().invoke(command).id
    }

    private fun userUpdateParams(entry: Map<String, String>?): UserUpdateParams {
        return UserUpdateParams(
            identifier = entry?.get("identifier") ?: context.userIds.lastUsedKey,
            givenName = entry?.get("givenName") ?: "John",
            familyName = entry?.get("familyName") ?: "Deuf",
            address = Address(
                street = entry?.get("street") ?: "street",
                postalCode = entry?.get("postalCode") ?: "12345",
                city = entry?.get("city") ?: "city"
            ),
            phone = entry?.get("phone") ?: "0600000000",
            roles = entry?.extractList<String>("roles")?.map { context.roleIdentifiers[it] ?: it }
                ?: listOfNotNull(context.roleIdentifiers.lastUsedOrNull),
            memberOf = entry?.get("memberOf").parseNullableOrDefault(null),
            attributes = userAttributesParams(entry),
        )
    }

    private data class UserUpdateParams(
        val identifier: TestContextKey,
        val givenName: String,
        val familyName: String,
        val address: Address?,
        val phone: String?,
        val roles: List<String>,
        val memberOf: OrganizationId?,
        val attributes: Map<String, String>,
    )

    private fun userAttributesParams(entry: Map<String, String>?): Map<String, String> {
        val job = entry?.get("job") ?: "job"
        return mapOf(
            "job" to job
        )
    }
}
