package io.komune.im.bdd

import io.cucumber.java8.En
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract

class RealmSelectSteps: En, ImCucumberStepsDefinition() {

    init {
        DataTableType(::authenticationParams)

        Given("I work on realm:") { params: RealmSelectParams ->
            step {
                context.realmId = context.realmIds[params.identifier] ?: params.identifier
            }
        }

        Given("I work on default realm") {
            step {
                context.realmId = "im-test"
            }
        }
    }

    private fun authenticationParams(entry: Map<String, String>) = RealmSelectParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class RealmSelectParams(
        val identifier: TestContextKey
    )
}
