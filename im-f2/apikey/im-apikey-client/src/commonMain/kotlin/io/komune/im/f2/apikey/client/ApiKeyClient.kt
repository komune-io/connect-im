package io.komune.im.f2.apikey.client

import io.komune.im.commons.model.AuthRealm
import f2.client.F2Client
import f2.client.function
import f2.dsl.fnc.F2SupplierSingle
import io.komune.im.apikey.domain.ApiKeyApi
import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddFunction
import io.komune.im.apikey.domain.command.ApikeyRemoveFunction
import io.komune.im.apikey.domain.query.ApiKeyGetFunction
import io.komune.im.apikey.domain.query.ApiKeyPageFunction
import kotlin.js.JsExport

expect fun F2Client.apiKeyClient(): F2SupplierSingle<ApiKeyClient>
expect fun apiKeyClient(urlBase: String, getAuth: suspend () -> AuthRealm): F2SupplierSingle<ApiKeyClient>

@JsExport
open class ApiKeyClient constructor(private val client: F2Client): ApiKeyApi {
    override fun apiKeyGet(): ApiKeyGetFunction = client.function(this::apiKeyGet.name)
    override fun apiKeyPage(): ApiKeyPageFunction = client.function(this::apiKeyPage.name)
    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = client.function(this::apiKeyCreate.name)
    override fun apiKeyRemove(): ApikeyRemoveFunction = client.function(this::apiKeyRemove.name)
}
