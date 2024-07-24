package io.komune.im.f2.privilege.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.client.ktor.http.plugin.F2Auth
import f2.client.ktor.http.plugin.model.AuthRealm
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson

actual fun F2Client.privilegeClient(): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(this)
}

actual fun privilegeClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(
        F2ClientBuilder.get(urlBase, json = null) {
            install(HttpTimeout) {
                @Suppress("MagicNumber")
                requestTimeoutMillis = 60000
            }
            install(F2Auth) {
                this.getAuth = getAuth
            }
            install(ContentNegotiation) {
                jackson {
                    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    propertyNamingStrategy = PropertyNamingStrategies.LOWER_CAMEL_CASE
                }
            }
        }
    )
}
