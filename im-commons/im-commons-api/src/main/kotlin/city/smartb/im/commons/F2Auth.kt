package city.smartb.im.commons

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.AuthRealmClientSecret
import city.smartb.im.commons.model.AuthRealmPassword
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.parametersOf
import io.ktor.util.AttributeKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO import F2's one when available
class F2Auth {
    private lateinit var authPlugin: Auth

    lateinit var getAuth: suspend () -> AuthRealm

    companion object Plugin: HttpClientPlugin<F2Auth, F2Auth> {
        private var lastBearerTokens: BearerTokens? = null

        override val key: AttributeKey<F2Auth> = AttributeKey("F2Auth")

        override fun prepare(block: F2Auth.() -> Unit): F2Auth {
            return F2Auth().apply {
                block()
                authPlugin = prepareAuth()
            }
        }

        override fun install(plugin: F2Auth, scope: HttpClient) {
            Auth.install(plugin.authPlugin, scope)
        }

        private fun F2Auth.prepareAuth() = Auth.prepare {
            bearer {
                loadTokens {
                    lastBearerTokens
                }
                refreshTokens {
                    val refreshTokenInfo: TokenInfo = client.post {
                        val authRealm = getAuth()
                        url("${authRealm.serverUrl}/realms/${authRealm.realmId}/protocol/openid-connect/token")
                        val parameters = if (oldTokens == null) {
                            when (authRealm) {
                                is AuthRealmClientSecret -> mapOf(
                                    "grant_type" to "client_credentials",
                                    "client_id" to authRealm.clientId,
                                    "client_secret" to authRealm.clientSecret,
                                )
                                is AuthRealmPassword -> mapOf(
                                    "grant_type" to "password",
                                    "client_id" to authRealm.clientId,
                                    "scope" to "openid",
                                    "username" to authRealm.username,
                                    "password" to authRealm.password
                                )
                            }
                        } else {
                            mapOf(
                                "grant_type" to "refresh_token",
                                "client_id" to authRealm.clientId,
                                "refresh_token" to (oldTokens?.refreshToken ?: "")
                            )
                        }.map { (key, value) -> key to listOf(value) }
                            .toTypedArray()
                        setBody(FormDataContent(parametersOf(*parameters)))
                        markAsRefreshTokenRequest()
                    }.body()
                    lastBearerTokens = BearerTokens(refreshTokenInfo.accessToken, refreshTokenInfo.refreshToken ?: "")
                    lastBearerTokens
                }
            }
        }
    }

    @Serializable
    private data class TokenInfo(
        @SerialName("access_token") val accessToken: String,
        @SerialName("expires_in") val expiresIn: Int,
        @SerialName("refresh_expires_in") val refreshExpiresIn: Int? = null,
        @SerialName("refresh_token") val refreshToken: String? = null,
        @SerialName("scope") val scope: String,
        @SerialName("token_type") val tokenType: String,
        @SerialName("id_token") val idToken: String? = null,
    )
}
