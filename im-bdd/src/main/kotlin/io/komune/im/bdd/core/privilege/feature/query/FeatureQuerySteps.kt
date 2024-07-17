package io.komune.im.bdd.core.privilege.feature.query

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.privilege.feature.data.feature
import io.komune.im.commons.utils.mapAsync
import io.komune.im.f2.privilege.domain.feature.model.Feature
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class FeatureQuerySteps: En, ImCucumberStepsDefinition() {
    init {
        DataTableType(::featureFetchedParams)

        Then("I should receive the feature") {
            step {
                assertFetchedFeatures(listOf(context.featureIdentifiers.lastUsedKey))
            }
        }

        Then("I should receive null instead of a feature") {
            step {
                assertFetchedFeatures(emptyList())
            }
        }

        Then("I should receive an empty list of features") {
            step {
                assertFetchedFeatures(emptyList())
            }
        }

        Then("I should receive a list of features:") { dataTable: DataTable ->
            step {
                dataTable.asList(FeatureFetchedParams::class.java)
                    .map(FeatureFetchedParams::identifier)
                    .let { assertFetchedFeatures(it) }
            }
        }
    }

    private suspend fun assertFetchedFeatures(identifiers: List<TestContextKey>) {
        val fetchedFeatures = context.fetched.features.filter { it.identifier !in context.permanentRoles() }
        val fetchedIdentifiers = fetchedFeatures.map(Feature::identifier)
        val expectedIdentifiers = identifiers.map(context.featureIdentifiers::safeGet)
        Assertions.assertThat(fetchedIdentifiers).containsExactlyInAnyOrderElementsOf(expectedIdentifiers)

        val featureAsserter = AssertionBdd.feature(keycloakClientProvider.get())
        fetchedFeatures.mapAsync { feature ->
            featureAsserter.assertThatId(feature.identifier).matches(feature)
        }
    }

    private fun featureFetchedParams(entry: Map<String, String>) = FeatureFetchedParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class FeatureFetchedParams(
        val identifier: TestContextKey
    )
}
