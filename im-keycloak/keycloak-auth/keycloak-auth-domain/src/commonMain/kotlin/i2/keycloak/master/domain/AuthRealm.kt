package i2.keycloak.master.domain

typealias RealmId = String

sealed class AuthRealm(
    open val serverUrl: String,
    open val realmId: RealmId,
    open val clientId: String,
    open val redirectUrl: String?,
)

class AuthRealmPassword(
    override val serverUrl: String,
    override val realmId: RealmId,
    override val redirectUrl: String,
    override val clientId: String,
    val username: String,
    val password: String,
): AuthRealm(serverUrl, realmId, clientId, redirectUrl)

class AuthRealmClientSecret(
    override val serverUrl: String,
    override val realmId: RealmId,
    override val clientId: String,
    override val redirectUrl: String?,
    val clientSecret: String,
): AuthRealm(serverUrl, realmId, clientId, redirectUrl)
