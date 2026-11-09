package io.komune.im.keycloak.plugin.client

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.parametersOf
import io.ktor.serialization.jackson.jackson
import org.keycloak.representations.AccessTokenResponse

class KeycloakPluginClient(
    private val keycloakBaseUrl: String
) {
    companion object {
        private val httpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                jackson()
            }
        }
    }

    /** Asks the plugin for an action token, authenticated by [bearerToken]; null unless Keycloak answers OK. */
    suspend fun generateActionToken(request: ActionTokenRequest, bearerToken: String): String? {
        val response = httpClient.get(actionTokenUrl(keycloakBaseUrl, request)) {
            header("Content-Type", ContentType.Application.Json)
            header("Authorization", "Bearer $bearerToken")
        }

        return response.body<String?>().takeIf { response.status == HttpStatusCode.OK }
    }

    suspend fun generateAccessToken(realm: String, clientId: String, clientSecret: String): String? {
        return httpClient.post("${buildUrl(realm)}/protocol/openid-connect/token") {
            setBody(
                mapOf(
                    "grant_type" to "client_credentials",
                    "client_id" to clientId,
                    "client_secret" to clientSecret
                ).mapValues { (_, value) -> listOf(value) }
                    .let { FormDataContent(parametersOf(it)) }
            )
        }.body<AccessTokenResponse>().token
    }

    private fun buildUrl(realm: String) = "${keycloakBaseUrl.removeSuffix("/")}/realms/$realm"
}

/** Where the action token of [request] is asked for, every parameter URL-encoded. */
internal fun actionTokenUrl(keycloakBaseUrl: String, request: ActionTokenRequest): String =
    URLBuilder("${keycloakBaseUrl.removeSuffix("/")}/realms/${request.realm}/${KeycloakPluginIds.ACTION_TOKEN}").apply {
        parameters.append("client_id", request.clientId)
        parameters.append("action", request.action)
        parameters.append("user_id", request.userId)
        request.redirectUri?.let { parameters.append("redirect_uri", it) }
    }.buildString()

/** The action token to ask for: who, in which realm, for which client and action. */
data class ActionTokenRequest(
    val realm: String,
    val clientId: String,
    val action: String,
    val userId: String,
    val redirectUri: String? = null,
)
