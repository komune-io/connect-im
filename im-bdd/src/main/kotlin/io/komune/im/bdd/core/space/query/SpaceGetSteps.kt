package io.komune.im.bdd.core.space.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.space.api.SpaceEndpoint
import io.komune.im.f2.space.domain.model.Space
import io.komune.im.f2.space.domain.query.SpaceGetQuery
import org.assertj.core.api.Assertions

class SpaceGetSteps(
    private val spaceEndpoint: SpaceEndpoint,
): En, ImCucumberStepsDefinition() {

    private var fetched: Space? = null

    init {
        When("I get the space") {
            step {
                fetched = SpaceGetQuery(id = context.spaceIdentifiers.lastUsed)
                    .invokeWith(spaceEndpoint.spaceGet())
                    .item
            }
        }

        Then("The fetched space should list locales {string}") { locales: String ->
            step {
                Assertions.assertThat(fetched?.locales).containsExactlyElementsOf(locales.split(","))
            }
        }
    }
}
