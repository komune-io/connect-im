package io.komune.im.f2.apikey.client

import f2.client.F2Client
import f2.client.domain.AuthRealm
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.http.plugin.F2Auth
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.komune.im.apikey.domain.ApiKeyApi
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddFunction
import io.komune.im.apikey.domain.command.ApikeyRemoveFunction
import io.komune.im.apikey.domain.query.ApiKeyGetFunction
import io.komune.im.apikey.domain.query.ApiKeyPageFunction
import io.ktor.client.plugins.HttpTimeout
import kotlin.js.JsExport


fun F2Client.apiKeyClient(): F2SupplierSingle<ApiKeyClient> = f2SupplierSingle {
    ApiKeyClient(this)
}

fun apiKeyClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<ApiKeyClient> = f2SupplierSingle {
    ApiKeyClient(
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
open class ApiKeyClient(private val client: F2Client): ApiKeyApi {
    override fun apiKeyGet(): ApiKeyGetFunction = client.function(this::apiKeyGet.name)
    override fun apiKeyPage(): ApiKeyPageFunction = client.function(this::apiKeyPage.name)
    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = client.function(this::apiKeyCreate.name)
    override fun apiKeyRemove(): ApikeyRemoveFunction = client.function(this::apiKeyRemove.name)
}
