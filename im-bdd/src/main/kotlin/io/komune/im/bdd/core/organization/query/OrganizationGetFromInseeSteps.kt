package io.komune.im.bdd.core.organization.query

import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class OrganizationGetFromInseeSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private var organizationFromInsee: Organization? = null

    init {
        DataTableType(::organizationGetFromInseeParams)

        When ("I get an organization by its siret number from Insee") {
            step {
                fetchOrganizationGetFromInsee(organizationGetFromInseeParams(null))
            }
        }

        When ("I get an organization by its siret number from Insee:") { params: OrganizationGetFromInseeParams ->
            step {
                fetchOrganizationGetFromInsee(params)
            }
        }

        Then("I should receive the organization from Insee") {
            step {
                Assertions.assertThat(organizationFromInsee).isNotNull
                Assertions.assertThat(organizationFromInsee!!.name).isNotNull
                Assertions.assertThat(organizationFromInsee!!.siret).isNotNull
                Assertions.assertThat(organizationFromInsee!!.attributes["original"]).isNotNull
            }
        }

        Then("I should not receive the organization from Insee") {
            step {
                Assertions.assertThat(organizationFromInsee).isNull()
            }
        }
    }

    private suspend fun fetchOrganizationGetFromInsee(params: OrganizationGetFromInseeParams) {
        organizationFromInsee = OrganizationGetFromInseeQuery(
            siret = params.siret
        ).invokeWith(organizationEndpoint.organizationGetFromInsee()).item
    }

    private fun organizationGetFromInseeParams(entry: Map<String, String>?) = OrganizationGetFromInseeParams(
        siret = entry?.get("siret") ?: "12345678912345"
    )

    private data class OrganizationGetFromInseeParams(
        val siret: String
    )
}
