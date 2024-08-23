package io.komune.im.f2.apikey.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.client.ktor.http.plugin.model.AuthRealm
import f2.dsl.fnc.F2SupplierSingle

import f2.dsl.fnc.f2SupplierSingle
import kotlinx.coroutines.await

@JsExport
actual fun apiKeyClient(urlBase: String, getAuth: suspend () -> AuthRealm): F2SupplierSingle<ApiKeyClient> = f2SupplierSingle {
    F2ClientBuilder.get(urlBase)
        .let(::ApiKeyClient)
}

actual fun F2Client.apiKeyClient(): F2SupplierSingle<ApiKeyClient> = f2SupplierSingle {
    ApiKeyClient(this)
}
