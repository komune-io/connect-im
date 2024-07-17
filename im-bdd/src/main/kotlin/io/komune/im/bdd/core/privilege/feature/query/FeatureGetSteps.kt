package io.komune.im.bdd.core.privilege.feature.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.privilege.api.FeatureEndpoint
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetQuery
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class FeatureGetSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var featureEndpoint: FeatureEndpoint

    init {
        DataTableType(::featureGetParams)

        When ("I get the feature") {
            step {
                featureGet(featureGetParams(null))
            }
        }

        When ("I get the feature:") { params: FeatureGetParams ->
            step {
                featureGet(params)
            }
        }
    }

    private suspend fun featureGet(params: FeatureGetParams) {
        context.fetched.features = listOfNotNull(
            FeatureGetQuery(
                identifier = context.featureIdentifiers[params.identifier] ?: params.identifier
            ).invokeWith(featureEndpoint.featureGet()).item
        )
    }

    private fun featureGetParams(entry: Map<String, String>?) = FeatureGetParams(
        identifier = entry?.get("identifier") ?: context.featureIdentifiers.lastUsedKey
    )

    private data class FeatureGetParams(
        val identifier: TestContextKey
    )
}
