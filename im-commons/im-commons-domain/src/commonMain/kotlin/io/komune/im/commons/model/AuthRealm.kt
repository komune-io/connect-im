package io.komune.im.commons.model

import kotlin.js.JsExport

typealias RealmId = String

@JsExport
sealed class AuthRealm(
    open val serverUrl: String,
    open val realmId: RealmId,
    open val clientId: String,
    open val redirectUrl: String?,
    open val space: String
)

@JsExport
data class AuthRealmPassword(
    override val serverUrl: String,
    override val realmId: RealmId,
    override val redirectUrl: String,
    override val clientId: String,
    val username: String,
    val password: String,
    override val space: String,
): AuthRealm(serverUrl, realmId, clientId, redirectUrl, space)

@JsExport
data class AuthRealmClientSecret(
    override val serverUrl: String,
    override val realmId: RealmId,
    override val clientId: String,
    override val redirectUrl: String?,
    val clientSecret: String,
    override val space: String,
): AuthRealm(serverUrl, realmId, clientId, redirectUrl, space)
