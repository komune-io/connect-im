package io.komune.im.f2.space.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.client.ktor.http.plugin.F2Auth
import f2.client.ktor.http.plugin.model.AuthRealm
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.ktor.client.plugins.HttpTimeout

actual fun F2Client.spaceClient(): F2SupplierSingle<SpaceClient> = f2SupplierSingle {
    SpaceClient(this)
}

actual fun spaceClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<SpaceClient> = f2SupplierSingle {
    SpaceClient(
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
