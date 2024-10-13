package io.komune.im.bdd.core.privilege.feature.command

import f2.dsl.fnc.invokeWith
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.privilege.feature.data.feature
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.f2.privilege.api.FeatureEndpoint
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineCommand
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.assertion.AssertionBdd

class FeatureDefineSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var featureEndpoint: FeatureEndpoint

    private lateinit var command: FeatureDefineCommand

    init {
        DataTableType(::featureDefineParams)

        When("I define a/the feature") {
            step {
                defineFeature(featureDefineParams(null))
            }
        }

        When("I define a/the feature:") { params: FeatureDefineParams ->
            step {
                defineFeature(params)
            }
        }

        Given("A/The feature is defined") {
            step {
                defineFeature(featureDefineParams(null))
            }
        }

        Given("A/The feature is defined:") { params: FeatureDefineParams ->
            step {
                defineFeature(params)
            }
        }

        Given("Some/The features are defined:") { dataTable: DataTable ->
            step {
                dataTable.asList(FeatureDefineParams::class.java)
                    .forEach { defineFeature(it) }
            }
        }

        Then("The feature should be defined") {
            step {
                AssertionBdd.feature(keycloakClientProvider.get()).assertThatId(command.identifier).hasFields(
                    identifier = command.identifier,
                    description = command.description,
                )
            }
        }
    }

    private suspend fun defineFeature(
        params: FeatureDefineParams
    ) = context.featureIdentifiers.register(params.identifier) {
        command = FeatureDefineCommand(
            identifier = params.identifier,
            description = params.description,
        )
        command.invokeWith(featureEndpoint.featureDefine()).identifier
    }

    private fun featureDefineParams(entry: Map<String, String>?): FeatureDefineParams {
        return FeatureDefineParams(
            identifier = entry?.get("identifier") ?: context.featureIdentifiers.lastUsedOrNull.orRandom(),
            description = entry?.get("description") ?: UUID.randomUUID().toString(),
        )
    }

    private data class FeatureDefineParams(
        val identifier: FeatureIdentifier,
        val description: String,
    )
}
