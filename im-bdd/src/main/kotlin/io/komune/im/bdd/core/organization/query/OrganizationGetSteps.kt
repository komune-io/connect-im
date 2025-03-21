package io.komune.im.bdd.core.organization.query

import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import io.komune.im.bdd.ImCucumberStepsDefinition
import io.komune.im.f2.organization.api.OrganizationEndpoint
import io.komune.im.f2.organization.domain.query.OrganizationGetQuery
import io.komune.im.f2.organization.domain.query.OrganizationRefGetQuery
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class OrganizationGetSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    init {
        DataTableType(::organizationGetParams)

        When ("I get an organization by ID") {
            step {
                organizationGet(organizationGetParams(null))
            }
        }

        When ("I get an organization by ID:") { params: OrganizationGetParams ->
            step {
                organizationGet(params)
            }
        }

        When ("I get an organization ref by ID") {
            step {
                organizationRefGet(organizationGetParams(null))
            }
        }

        When ("I get an organization ref by ID:") { params: OrganizationGetParams ->
            step {
                organizationRefGet(params)
            }
        }
    }

    private suspend fun organizationGet(params: OrganizationGetParams) {
        context.fetched.organizations = listOfNotNull(
            OrganizationGetQuery(
                id = params.identifier
            )
                .invokeWith(organizationEndpoint.organizationGet())
                .item
        )
    }

    private suspend fun organizationRefGet(params: OrganizationGetParams) {
        context.fetched.organizationRefs = listOfNotNull(
            OrganizationRefGetQuery(
                id = params.identifier
            )
                .invokeWith(organizationEndpoint.organizationRefGet())
                .item
        )
    }

    private fun organizationGetParams(entry: Map<String, String>?) = OrganizationGetParams(
        identifier = entry?.get("identifier") ?: context.organizationIds.lastUsed
    )

    private data class OrganizationGetParams(
        val identifier: TestContextKey
    )
}
