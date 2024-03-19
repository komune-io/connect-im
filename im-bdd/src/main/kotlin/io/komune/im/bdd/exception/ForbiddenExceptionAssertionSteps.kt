package io.komune.im.bdd.exception

import f2.spring.exception.ForbiddenAccessException
import io.cucumber.java8.En
import s2.bdd.S2CucumberStepsDefinition
import s2.bdd.assertion.AssertionBdd
import s2.bdd.assertion.exceptions

class ForbiddenExceptionAssertionSteps: En, S2CucumberStepsDefinition() {
    init {
        Then("I should be forbidden to do so") {
            step {
                AssertionBdd.exceptions(context)
                    .assertThat(ForbiddenAccessException::class)
                    .hasBeenThrown(1)
            }
        }

        Then("I should not be forbidden to do so") {
            step {
                AssertionBdd.exceptions(context)
                    .assertThat(ForbiddenAccessException::class)
                    .hasNotBeenThrown()
            }
        }
    }
}
