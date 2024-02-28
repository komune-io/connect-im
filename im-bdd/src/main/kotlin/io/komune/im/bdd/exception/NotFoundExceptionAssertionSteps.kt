package io.komune.im.bdd.exception

import f2.spring.exception.NotFoundException
import io.cucumber.java8.En
import s2.bdd.S2CucumberStepsDefinition
import s2.bdd.assertion.AssertionBdd
import s2.bdd.assertion.exceptions
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class NotFoundExceptionAssertionSteps: En, S2CucumberStepsDefinition() {
    init {
        DataTableType(::notFoundParams)

        Then("The {string} should not be found") { objectName: String ->
            val lastUsedKey = context.entityLists[objectName]!!.lastUsedKey
            assert(lastUsedKey.toString())
        }

    }

    private fun assert(id: String) = step {
        AssertionBdd.exceptions(context)
            .assertThat(NotFoundException::class)
            .hasBeenThrown(1) { e -> id in e.message.orEmpty() }
    }

    private fun notFoundParams(entry: Map<String, String>) = NotFoundParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class NotFoundParams(
        val identifier: TestContextKey
    )
}
