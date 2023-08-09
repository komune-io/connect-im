package im.script.gateway

import im.script.gateway.conguration.config.ImScriptConfigProperties
import im.script.gateway.conguration.retryWithExceptions
import im.script.gateway.conguration.config.ImScriptInitProperties
import im.script.gateway.conguration.config.base.toAuthRealm
import im.script.function.init.KeycloakInitScript
import i2.keycloak.master.domain.AuthRealm
import im.script.function.config.KeycloakConfigScript
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
            val auth = imScriptInitProperties.auth.toAuthRealm()
            runInit(auth, json)
        }
        imScriptConfigProperties.json?.let { json ->
            val auth = imScriptConfigProperties.auth.toAuthRealm()
            runConfig(auth, json)
        }
        context.close()
    }
    suspend fun runInit(authRealm: AuthRealm, json: String) {
        val success = retryWithExceptions("Init", imScriptInitProperties.retry.max, imScriptInitProperties.retry.delayMillis, logger) {
            keycloakInitService.run(authRealm, json)
        }
        if (!success) {
            logger.error("Could not initialize Keycloak. Exiting application.")
            // Handle the situation when initialization failed after all attempts
        }
    }

    suspend fun runConfig(authRealm: AuthRealm, json: String) {
        val success = retryWithExceptions(
            "Config",
            imScriptConfigProperties.retry.max,
            imScriptConfigProperties.retry.delayMillis,
            logger
        ) {
            keycloakConfigService.run(authRealm, json)
        }
        if (!success) {
            logger.error("Could not configure Keycloak. Exiting application.")
            // Handle the situation when initialization failed after all attempts
        }
    }
}
