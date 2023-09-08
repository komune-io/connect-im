package city.smartb.im.bdd.core.user.command

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.bdd.core.user.data.user
import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.command.UserUpdateCommandDTOBase
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import java.util.UUID

class UserUpdateSteps: En, ImCucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserUpdateCommandDTOBase

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

                AssertionBdd.user(keycloakClient()).assertThatId(userId).hasFields(
                    givenName = command.givenName,
                    familyName = command.familyName,
                    address = command.address,
                    phone = command.phone,
                    roles = command.roles,
                    attributes = command.attributes.orEmpty(),
                )
            }
        }
    }

    private suspend fun updateUser(params: UserUpdateParams) {
        command = UserUpdateCommandDTOBase(
            id = context.userIds.safeGet(params.identifier),
            givenName = params.givenName,
            familyName = params.familyName,
            address = params.address,
            phone = params.phone,
            roles = params.roles,
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
            roles = listOfNotNull(context.roleIdentifiers.lastUsedOrNull),
            memberOf = entry?.get("memberOf").parseNullableOrDefault(context.organizationIds.lastUsedOrNull),
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
        val job = entry?.get("job") ?: "job-${UUID.randomUUID()}"
        return mapOf(
            "job" to job
        )
    }
}
