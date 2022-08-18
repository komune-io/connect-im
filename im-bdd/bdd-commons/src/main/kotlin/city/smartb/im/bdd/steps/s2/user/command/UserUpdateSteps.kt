package city.smartb.im.bdd.steps.s2.user.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.data.parser.parseNullableOrDefault
import city.smartb.im.bdd.steps.s2.user.assertion.user
import city.smartb.im.commons.model.Address
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.command.UserUpdateCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import java.util.UUID
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class UserUpdateSteps: En, CucumberStepsDefinition() {
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

                AssertionBdd.user(userEndpoint).assertThat(userId).hasFields(
                    givenName = command.givenName,
                    familyName = command.familyName,
                    address = command.address,
                    phone = command.phone,
                    roles = command.roles,
                    sendEmailLink = command.sendEmailLink,
                    memberOf = command.memberOf,
                    attributes = buildAttributesMap(command.attributes, command.memberOf).orEmpty(),
                )
            }
        }

        Then("The user's organization should not be updated") {
            step {
                val user = AssertionBdd.user(userEndpoint).get(context.userIds.lastUsed)
                Assertions.assertThat(user?.memberOf).isNotEqualTo(command.memberOf)
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
            sendEmailLink = params.sendEmailLink,
            memberOf = params.memberOf,
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
            roles = listOfNotNull(context.roleIds.lastUsedOrNull),
            sendEmailLink = false,
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
        val sendEmailLink: Boolean,
        val memberOf: OrganizationId?,
        val attributes: Map<String, String>,
    )

    private fun userAttributesParams(entry: Map<String, String>?): Map<String, String> {
        val job = entry?.get("job") ?: "job-${UUID.randomUUID()}"
        return mapOf(
            "job" to job
        )
    }

    private fun buildAttributesMap(attributes: Map<String, String>?, memberOf: OrganizationId?): Map<String, String>? {
        if (memberOf == null) return attributes
        return command.attributes.orEmpty().plus("memberOf" to memberOf)
    }
}

