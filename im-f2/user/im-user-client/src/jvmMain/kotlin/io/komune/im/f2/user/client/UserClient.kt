package io.komune.im.f2.user.client

import io.komune.im.commons.F2Auth
import io.komune.im.commons.model.AuthRealm
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.ktor.client.plugins.HttpTimeout

actual fun F2Client.userClient(): F2SupplierSingle<UserClient> = f2SupplierSingle {
    UserClient(this)
}

actual fun userClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<UserClient> = f2SupplierSingle {
    UserClient(
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
