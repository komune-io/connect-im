package io.komune.im.f2.organization.client

import f2.client.F2Client
import f2.client.domain.AuthRealm
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.http.plugin.F2Auth
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.komune.im.f2.organization.domain.OrganizationApi
import io.komune.im.f2.organization.domain.command.OrganizationCreateFunction
import io.komune.im.f2.organization.domain.command.OrganizationDeleteFunction
import io.komune.im.f2.organization.domain.command.OrganizationDisableFunction
import io.komune.im.f2.organization.domain.command.OrganizationUpdateFunction
import io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeFunction
import io.komune.im.f2.organization.domain.query.OrganizationGetFunction
import io.komune.im.f2.organization.domain.query.OrganizationPageFunction
import io.komune.im.f2.organization.domain.query.OrganizationRefGetFunction
import io.komune.im.f2.organization.domain.query.OrganizationRefListFunction
import io.ktor.client.plugins.HttpTimeout
import kotlin.js.JsExport

fun F2Client.organizationClient(): F2SupplierSingle<OrganizationClient> = f2SupplierSingle {
    OrganizationClient(this)
}

fun organizationClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,

    ): F2SupplierSingle<OrganizationClient> = f2SupplierSingle {
    OrganizationClient(
        F2ClientBuilder.get(urlBase) {
            install(HttpTimeout) {
                @Suppress("MagicNumber")
                requestTimeoutMillis = 60000
            }
            install(F2Auth) {
                this.getAuth = getAuth
            }
        }
    )
}

@JsExport
open class OrganizationClient(private val client: F2Client) : OrganizationApi {

    override fun organizationGet(): OrganizationGetFunction = client.function(this::organizationGet.name)
    override fun organizationRefGet(): OrganizationRefGetFunction = client.function(this::organizationRefGet.name)

    override fun organizationGetFromInsee(): OrganizationGetFromInseeFunction =
        client.function(this::organizationGetFromInsee.name)

    override fun organizationPage(): OrganizationPageFunction = client.function(this::organizationPage.name)
    override fun organizationRefList(): OrganizationRefListFunction = client.function(this::organizationRefList.name)

    override fun organizationCreate(): OrganizationCreateFunction = client.function(this::organizationCreate.name)
    override fun organizationUpdate(): OrganizationUpdateFunction = client.function(this::organizationUpdate.name)
    override fun organizationDisable(): OrganizationDisableFunction = client.function(this::organizationDisable.name)
    override fun organizationDelete(): OrganizationDeleteFunction = client.function(this::organizationDelete.name)

}
