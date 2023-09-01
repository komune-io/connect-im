package city.smartb.im.script.gateway

import city.smartb.im.script.core.config.properties.ImScriptConfigProperties
import city.smartb.im.script.core.config.properties.ImScriptInitProperties
import city.smartb.im.script.core.retryWithExceptions
import city.smartb.im.script.space.config.KeycloakConfigScript
import city.smartb.im.script.space.create.KeycloakInitScript
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class ScriptServiceRunner(
    private val context: ConfigurableApplicationContext,
    private val keycloakInitService: KeycloakInitScript,
    private val keycloakConfigService: KeycloakConfigScript,
    private val imScriptInitProperties: ImScriptInitProperties,
    private val imScriptConfigProperties: ImScriptConfigProperties,
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(ScriptServiceRunner::class.java)

    override fun run(vararg args: String?) = runBlocking {
        imScriptInitProperties.json?.let { json ->
            runInit(json)
        }
        imScriptConfigProperties.json?.let { json ->
            runConfig(json)
        }
        context.close()
    }

    suspend fun runInit(json: String) {
        val success = retryWithExceptions(
            actionName = "Init",
            maxRetries = imScriptInitProperties.retry.max,
            retryDelayMillis = imScriptInitProperties.retry.delayMillis,
            logger = logger
        ) {
            keycloakInitService.run(json)
        }
        if (!success) {
            logger.error("Could not initialize Keycloak. Exiting application.")
            // Handle the situation when initialization failed after all attempts
        }
    }

    suspend fun runConfig(json: String) {
        val success = retryWithExceptions(
            actionName = "Config",
            maxRetries = imScriptConfigProperties.retry.max,
            retryDelayMillis = imScriptConfigProperties.retry.delayMillis,
            logger = logger
        ) {
            keycloakConfigService.run(json)
        }
        if (!success) {
            logger.error("Could not configure Keycloak. Exiting application.")
            // Handle the situation when initialization failed after all attempts
        }
    }
}
