package i2.config.api

import i2.app.core.retryWithExceptions
import i2.config.api.auth.KeycloakConfigScript
import i2.config.api.config.KcConfigProperties
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class ConfigService(
    private val context: ConfigurableApplicationContext,
    private val keycloakConfigService: KeycloakConfigScript,
    private val keycloakConfig: KcConfigProperties
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(ConfigService::class.java)
    override fun run(vararg args: String?) = runBlocking {
        val success = retryWithExceptions(keycloakConfig.maxRetries, keycloakConfig.retryDelayMillis, logger) {
            keycloakConfigService.run(keycloakConfig.json)
        }
        if (!success) {
            logger.error("Could not initialize Keycloak. Exiting application.")
        }
        context.close()
    }
}
