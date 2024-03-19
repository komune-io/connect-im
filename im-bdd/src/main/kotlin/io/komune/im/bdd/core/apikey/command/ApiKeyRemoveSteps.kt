package io.komune.im.bdd.core.apikey.command

import io.komune.im.apikey.api.ApiKeyEndpoint
import io.komune.im.apikey.domain.command.ApikeyRemoveCommand
import io.komune.im.apikey.domain.model.ApiKeyId
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.apikey.data.apiKey
import io.komune.im.bdd.core.apikey.data.client
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class ApiKeyRemoveSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var apikeyEndpoint: ApiKeyEndpoint

    private lateinit var command: ApikeyRemoveCommand

    init {
        DataTableType(::apiKeyRemoveParams)
        DataTableType(::apiKeyAssertParams)

        When("I remove an API key") {
            step {
                removeApiKey(apiKeyRemoveParams(null))
            }
        }

        When("I remove an API key:") { params: ApiKeyRemoveParams ->
            step {
                removeApiKey(params)
            }
        }

        Given("An API key is removed") {
            step {
                removeApiKey(apiKeyRemoveParams(null))
            }
        }

        Given("An API key is removed:") { params: ApiKeyRemoveParams ->
            step {
                removeApiKey(params)
            }
        }

        Given("Some API keys are removed:") { dataTable: DataTable ->
            step {
                dataTable.asList(ApiKeyRemoveParams::class.java)
                    .forEach { removeApiKey(it) }
            }
        }

        Then("The API key should be removed") {
            step {
                assertApiKey(command.id)
            }
        }

        Then("The API key should be removed:") { params: ApiKeyAssertParams ->
            step {
                assertApiKey(context.apikeyIds.safeGet(params.identifier))
            }
        }
    }

    private suspend fun removeApiKey(params: ApiKeyRemoveParams) {
        command = ApikeyRemoveCommand(
            id = context.apikeyIds[params.identifier] ?: params.identifier
        )
        context.apikeyIds[params.identifier] = null
        apikeyEndpoint.apiKeyRemove().invoke(command)
    }

    private suspend fun assertApiKey(id: ApiKeyId) {
        AssertionBdd.client(keycloakClient()).notExists(id)
        AssertionBdd.apiKey(keycloakClient()).notExists(id)
    }

    private fun apiKeyRemoveParams(entry: Map<String, String>?) = ApiKeyRemoveParams(
        identifier = entry?.get("identifier") ?: context.apikeyIds.lastUsedKey
    )

    private fun apiKeyAssertParams(entry: Map<String, String>) = ApiKeyAssertParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class ApiKeyRemoveParams(
        val identifier: TestContextKey,
    )

    private data class ApiKeyAssertParams(
        val identifier: TestContextKey,
    )
}
