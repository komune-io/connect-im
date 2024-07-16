package io.komune.im.bdd.core.privilege.feature.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.privilege.api.FeatureEndpoint
import io.komune.im.f2.privilege.domain.feature.query.FeatureListQuery
import org.springframework.beans.factory.annotation.Autowired

class FeatureListSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var featureEndpoint: FeatureEndpoint

    init {
        When ("I list the features") {
            step {
                featureList()
            }
        }
    }

    private suspend fun featureList() {
        context.fetched.features = FeatureListQuery()
            .invokeWith(featureEndpoint.featureList())
            .items
    }
}
