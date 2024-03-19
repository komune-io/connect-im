package io.komune.im.bdd.core.apikey.command

import io.komune.im.apikey.api.ApiKeyEndpoint
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddKeyCommand
import io.komune.im.apikey.domain.model.ApiKeyIdentifier
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.apikey.data.apiKey
import io.komune.im.bdd.core.apikey.data.client
import io.komune.im.bdd.core.user.data.user
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.extractList

class ApiKeyCreateSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var apikeyEndpoint: ApiKeyEndpoint

    private lateinit var command: ApiKeyOrganizationAddKeyCommand

    init {
        DataTableType(::apiKeyCreateParams)
        DataTableType(::apiKeyAssertParams)

        When("I create an API key") {
            step {
                createApiKey(apiKeyCreateParams(null))
            }
        }

        When("I create an API key:") { params: ApiKeyCreateParams ->
            step {
                createApiKey(params)
            }
        }

        Given("An API key is created") {
            step {
                createApiKey(apiKeyCreateParams(null))
            }
        }

        Given("An API key is created:") { params: ApiKeyCreateParams ->
            step {
                createApiKey(params)
            }
        }

        Given("Some API keys are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(ApiKeyCreateParams::class.java)
                    .forEach { createApiKey(it) }
            }
        }

        Then("The API key should be created") {
            step {
                val params = ApiKeyAssertParams(
                    identifier = context.apikeyIds.lastUsedKey,
                    keyIdentifier = null,
                    organization = null,
                    name = command.name,
                    secret = command.secret,
                    roles = command.roles
                )
                assertApiKey(params)
            }
        }

        Then("The API key should be created:") { params: ApiKeyAssertParams ->
            step {
                assertApiKey(params)
            }
        }
    }

    private suspend fun createApiKey(params: ApiKeyCreateParams) = context.apikeyIds.register(params.identifier) {
        command = ApiKeyOrganizationAddKeyCommand(
            name = params.name,
            organizationId = context.organizationIds[params.organization] ?: params.organization,
            roles = params.roles,
        )
        apikeyEndpoint.apiKeyCreate().invoke(command).id
    }

    private suspend fun assertApiKey(params: ApiKeyAssertParams) {
        val kcClient = keycloakClient()
        val assertionApiKey = AssertionBdd.apiKey(kcClient)
        val apiKeyId = context.apikeyIds.safeGet(params.identifier)
        val apiKey = assertionApiKey.findById(apiKeyId)

        Assertions.assertThat(apiKey).isNotNull
        assertionApiKey.assertThat(apiKey!!).hasFields(
            identifier = params.keyIdentifier ?: apiKey.identifier,
            name = params.name ?: apiKey.name,
            roles = params.roles ?: apiKey.roles,
        )

        AssertionBdd.client(kcClient).assertThatId(apiKeyId).hasFields(
            identifier = apiKey.identifier,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            isStandardFlowEnabled = false
        )

        val user = kcClient.client(apiKeyId).serviceAccountUser
        AssertionBdd.user(kcClient).assertThat(user).hasFields(
            memberOf = params.organization?.let(context.organizationIds::safeGet) ?: user.attributes["memberOf"]?.firstOrNull(),
            roles = params.roles ?: apiKey.roles,
            enabled = true,
            isApiKey = true
        )
    }

    private fun apiKeyCreateParams(entry: Map<String, String>?): ApiKeyCreateParams {
        val identifier = entry?.get("identifier").orRandom()
        return ApiKeyCreateParams(
            identifier = identifier,
            name = entry?.get("name") ?: "apikey-${identifier}",
            organization = entry?.get("organization") ?: context.organizationIds.lastUsedKey,
            roles = entry?.extractList("roles").orEmpty()
        )
    }

    private fun apiKeyAssertParams(entry: Map<String, String>) = ApiKeyAssertParams(
        identifier = entry["identifier"] ?: context.apikeyIds.lastUsedKey,
        keyIdentifier = entry["keyIdentifier"],
        organization = entry["organization"],
        name = entry["name"],
        secret = entry["secret"],
        roles = entry.extractList("roles")
    )

    private data class ApiKeyCreateParams(
        val identifier: TestContextKey,
        val organization: TestContextKey,
        val name: String,
        val secret: String? = null,
        val roles: List<TestContextKey>
    )

    private data class ApiKeyAssertParams(
        val identifier: TestContextKey,
        val keyIdentifier: ApiKeyIdentifier?,
        val organization: TestContextKey?,
        val name: String?,
        val secret: String?,
        val roles: List<TestContextKey>?
    )
}
