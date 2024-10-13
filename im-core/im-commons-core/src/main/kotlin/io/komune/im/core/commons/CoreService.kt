package io.komune.im.core.commons

import f2.dsl.cqrs.exception.F2Exception
import io.komune.im.commons.exception.I2ApiError
import io.komune.im.commons.exception.IMException
import io.komune.im.commons.exception.asException
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.infra.redis.CacheName
import io.komune.im.infra.redis.CachedService
import kotlinx.coroutines.CoroutineScope
import org.springframework.beans.factory.annotation.Autowired

open class CoreService(
    cacheName: CacheName
): CachedService(cacheName) {

    @Autowired
    protected lateinit var keycloakClientProvider: KeycloakClientProvider

    protected suspend inline fun <reified R> query(
        id: String,
        errorMessage: String,
        crossinline fetch: suspend CoroutineScope.() -> R
    ): R = query(id) {
        handleErrors(errorMessage) { fetch() }
    }

    protected suspend inline fun <reified R> mutate(
        id: String,
        errorMessage: String,
        crossinline exec: suspend CoroutineScope.() -> R
    ): R = mutate(id) {
        handleErrors(errorMessage) { exec() }
    }

    @Suppress("ThrowsCount")
    protected suspend fun <R> handleErrors(errorMessage: String, exec: suspend () -> R): R {
        return try {
            exec()
        } catch (e: IMException) {
            throw e
        } catch (e: F2Exception) {
            throw e
        } catch (e: Exception) {
            val client = keycloakClientProvider.get()
            throw I2ApiError(
                description = "Space [${client.realmId}, ${client.auth.realmId}]: $errorMessage",
                payload = emptyMap()
            ).asException(e)
        }
    }
}
