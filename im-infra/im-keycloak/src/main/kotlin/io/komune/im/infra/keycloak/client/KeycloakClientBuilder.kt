package io.komune.im.infra.keycloak.client

import f2.client.domain.AuthRealmClientSecret
import f2.client.domain.AuthRealmPassword
import f2.client.domain.RealmId
import io.komune.im.commons.model.AuthSubRealm
import io.komune.im.infra.keycloak.AuthRealmException
import org.jboss.resteasy.client.jaxrs.ResteasyClient
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit


object KeycloakClientBuilder {
    private const val CONNECTION_TTL: Long = 30
    private const val CONNECTION_POOL_SIZE = 10
    private const val MAX_PER_ROUTE = 20
    private val logger = LoggerFactory.getLogger(KeycloakClientBuilder::class.java)

    private var resteasyClient: ResteasyClient = createClient()

    private fun createClient(): ResteasyClient {
        logger.info("Creating shared RESTEasy client with connection pool")
        val builder = ResteasyClientBuilder.newBuilder() as ResteasyClientBuilder
        return builder.connectionPoolSize(CONNECTION_POOL_SIZE)
            .connectionPoolSize(CONNECTION_POOL_SIZE)
            .maxPooledPerRoute(MAX_PER_ROUTE)
            .connectionTTL(CONNECTION_TTL, TimeUnit.SECONDS)
            .build()
    }

    fun create() {
        if (resteasyClient.isClosed == true) {
            resteasyClient = createClient()
            logger.info("Created new RESTEasy client")
        } else {
            logger.info("RESTEasy client already exists and is not closed")
        }
    }

    fun getConnection(auth: AuthSubRealm): KeycloakClientConnection {
        logger.debug("Open Keycloak connection for {}", auth)
        val master = auth.master
        return when (master) {
            is AuthRealmPassword -> getConnection(auth) {
                grantType(OAuth2Constants.PASSWORD)
                username(master.username)
                password(master.password)
            }

            is AuthRealmClientSecret -> getConnection(auth) {
                grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                clientSecret(master.clientSecret)
            }

            else -> throw AuthRealmException("Invalid AuthRealm type[${auth::class.simpleName}]")
        }
    }

    private fun getConnection(
        auth: AuthSubRealm,
        configure: KeycloakBuilder.() -> KeycloakBuilder
    ): KeycloakClientConnection {
        val keycloak = KeycloakBuilder.builder()
            .serverUrl(auth.master.serverUrl)
            .realm(auth.master.realmId)
            .clientId(auth.master.clientId)
            .resteasyClient(resteasyClient)
            .configure()
            .build()
        return KeycloakClientConnection(keycloak, auth)
    }

    class KeycloakClientConnection(
        val keycloak: Keycloak,
        val auth: AuthSubRealm
    ) {
        fun forRealm(realmId: RealmId?) = KeycloakClient(keycloak, auth.master, realmId ?: auth.space)
        fun forAuthedRealm() = forRealm(null)
    }

    fun close() {
        resteasyClient.close()
        logger.info("Closed and cleared RESTEasy client")
    }
}
