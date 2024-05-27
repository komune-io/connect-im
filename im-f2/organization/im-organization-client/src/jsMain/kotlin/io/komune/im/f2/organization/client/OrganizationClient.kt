package io.komune.im.f2.organization.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.client.ktor.http.plugin.model.AuthRealm
import f2.dsl.fnc.F2SupplierSingle

import f2.dsl.fnc.f2SupplierSingle
import kotlinx.coroutines.await

@JsExport
actual fun organizationClient(urlBase: String, getAuth: suspend () -> AuthRealm): F2SupplierSingle<OrganizationClient> = f2SupplierSingle {
    F2ClientBuilder.get(urlBase)
        .await()
        .let(::OrganizationClient)
}

actual fun F2Client.organizationClient(): F2SupplierSingle<OrganizationClient> = f2SupplierSingle {
    OrganizationClient(this)
}
