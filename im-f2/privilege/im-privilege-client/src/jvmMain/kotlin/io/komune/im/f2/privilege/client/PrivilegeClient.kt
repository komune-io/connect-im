package io.komune.im.f2.privilege.client

import io.komune.im.commons.F2Auth
import io.komune.im.commons.model.AuthRealm
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.ktor.client.plugins.HttpTimeout

actual fun F2Client.privilegeClient(): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(this)
}

actual fun privilegeClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(
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
