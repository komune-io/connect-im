package io.komune.im.bdd.core.space.command

import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.api.config.properties.IMProperties
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.apikey.data.client
import io.komune.im.bdd.core.space.data.space
import io.komune.im.commons.model.SpaceIdentifier
import io.komune.im.f2.space.api.SpaceEndpoint
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd

class SpaceCreateSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var imConfig: IMProperties

    @Autowired
    private lateinit var spaceEndpoint: SpaceEndpoint

    private lateinit var command: SpaceDefineCommand

    init {
        DataTableType(::spaceCreateParams)

        When("I create a space") {
            step {
                createSpace(spaceCreateParams(null))
            }
        }

        When("I create a space:") { params: SpaceCreateParams ->
            step {
                createSpace(params)
            }
        }

        Given("A space is created") {
            step {
                createSpace(spaceCreateParams(null))
            }
        }

        Given("A space is created:") { params: SpaceCreateParams ->
            step {
                createSpace(params)
            }
        }

        Given("Some spaces are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(SpaceCreateParams::class.java)
                    .forEach { createSpace(it) }
            }
        }

        Then("The space should be created") {
            step {
                withAuth("master") {
                    val keycloakClient = keycloakClientProvider.get()
                    AssertionBdd.space(keycloakClient).exists(command.identifier)

                    val spaceMasterClient = keycloakClient.getClientByIdentifier("${command.identifier}-realm")!!
                    val spaceMasterClientRoles = keycloakClient.client(spaceMasterClient.id).roles().list()
                    AssertionBdd.client(keycloakClient).assertThatIdentifier(imConfig.keycloak.clientId)
                        .hasClientRoles(spaceMasterClient.id, spaceMasterClientRoles.map { it.name })
                }
            }
        }
    }

    private suspend fun createSpace(params: SpaceCreateParams) = context.spaceIdentifiers.register(params.identifier) {
        command = SpaceDefineCommand(
            identifier = params.identifier,
            smtp = null,
            theme = null,
            locales = null,
            settings = null
        )
        command.invokeWith(spaceEndpoint.spaceDefine()).identifier
    }

    private fun spaceCreateParams(entry: Map<String, String>?): SpaceCreateParams {
        return SpaceCreateParams(
            identifier = entry?.get("identifier").orRandom()
        )
    }

    private data class SpaceCreateParams(
        val identifier: SpaceIdentifier,
    )
}
