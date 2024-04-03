package io.komune.im.f2.privilege.client

import io.komune.im.commons.model.AuthRealm
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle

import f2.dsl.fnc.f2SupplierSingle
import kotlinx.coroutines.await

@JsExport
actual fun privilegeClient(urlBase: String, getAuth: suspend () -> AuthRealm): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    F2ClientBuilder.get(urlBase)
        .await()
        .let(::PrivilegeClient)
}

actual fun F2Client.privilegeClient(): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(this)
}
