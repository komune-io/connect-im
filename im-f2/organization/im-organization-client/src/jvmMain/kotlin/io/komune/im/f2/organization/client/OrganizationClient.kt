package io.komune.im.f2.organization.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.get
import f2.client.ktor.http.F2DefaultJson
import f2.client.ktor.http.plugin.F2Auth
import f2.client.ktor.http.plugin.model.AuthRealm
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import kotlinx.serialization.json.Json

actual fun F2Client.organizationClient(): F2SupplierSingle<OrganizationClient> = f2SupplierSingle {
    OrganizationClient(this)
}

actual fun organizationClient(
    urlBase: String,
    json: Json?,
    getAuth: suspend () -> AuthRealm,

    ): F2SupplierSingle<OrganizationClient> = f2SupplierSingle {
    OrganizationClient(
        F2ClientBuilder.get(urlBase, json = json) {
            install(HttpTimeout) {
                @Suppress("MagicNumber")
                requestTimeoutMillis = 60000
            }
            install(F2Auth) {
                this.getAuth = getAuth
            }
            if(json == null) {
                install(ContentNegotiation) {
                    jackson {
                        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                        propertyNamingStrategy = PropertyNamingStrategies.LOWER_CAMEL_CASE
                    }
                }
            }
        }
    )
}
