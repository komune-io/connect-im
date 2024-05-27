package io.komune.im.f2.space.client

import io.komune.im.commons.model.AuthRealm
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle

import f2.dsl.fnc.f2SupplierSingle
import kotlinx.coroutines.await

@JsExport
actual fun spaceClient(urlBase: String, getAuth: suspend () -> AuthRealm): F2SupplierSingle<SpaceClient> = f2SupplierSingle {
    F2ClientBuilder.get(urlBase)
        .await()
        .let(::SpaceClient)
}

actual fun F2Client.spaceClient(): F2SupplierSingle<SpaceClient> = f2SupplierSingle {
    SpaceClient(this)
}
