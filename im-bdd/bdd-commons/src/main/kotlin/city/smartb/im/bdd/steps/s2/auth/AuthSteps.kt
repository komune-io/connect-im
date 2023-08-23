package city.smartb.im.bdd.steps.s2.auth

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.commons.auth.AuthedUser
import city.smartb.im.commons.auth.Roles
import io.cucumber.java8.En

class AuthSteps: En, CucumberStepsDefinition() {

    init {
        Given("I am logged in as an admin") {
            step {
                context.authedUser = AuthedUser(
                    id = "admin",
                    roles = Roles.values().map { it.value }.toTypedArray(),
                    memberOf = null
                )
            }
        }
    }
}
