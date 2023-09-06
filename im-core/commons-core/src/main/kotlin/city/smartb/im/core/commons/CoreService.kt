package city.smartb.im.core.commons

import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.CachedService
import f2.dsl.cqrs.exception.F2Exception
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.I2Exception
import i2.keycloak.f2.commons.domain.error.asI2Exception
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
        } catch (e: I2Exception) {
            throw e
        } catch (e: F2Exception) {
            throw e
        } catch (e: Exception) {
            val client = keycloakClientProvider.get()
            throw I2ApiError(
                description = "Space [${client.auth.space}]: $errorMessage",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
