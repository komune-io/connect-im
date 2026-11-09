package io.komune.im.bdd.core.space.command

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.bdd.core.space.data.space
import io.komune.im.f2.space.api.SpaceEndpoint
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import s2.bdd.assertion.AssertionBdd

class SpaceUpdateSteps(
    private val spaceEndpoint: SpaceEndpoint,
): En, ImCucumberStepsDefinition() {

    init {
        Given("The space has internationalization disabled") {
            step {
                withAuth("master") {
                    val realm = keycloakClientProvider.getClient().realm(context.spaceIdentifiers.lastUsed)
                    realm.update(realm.toRepresentation().apply { isInternationalizationEnabled = false })
                }
            }
        }

        When("I update the space with locales {string}") { locales: String ->
            step {
                SpaceDefineCommand(
                    identifier = context.spaceIdentifiers.lastUsed,
                    displayName = null,
                    smtp = null,
                    theme = null,
                    locales = locales.split(","),
                    mfa = null,
                    settings = null,
                ).invokeWith(spaceEndpoint.spaceDefine())
            }
        }

        Then("The space should have default locale {string} and locales {string}") {
                defaultLocale: String, locales: String ->
            step {
                withAuth("master") {
                    AssertionBdd.space(keycloakClientProvider.getClient())
                        .assertThatId(context.spaceIdentifiers.lastUsed)
                        .hasLocales(defaultLocale, locales.split(","))
                }
            }
        }
    }
}
