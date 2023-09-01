package city.smartb.im.script.gateway

import city.smartb.im.script.core.config.properties.ImRetryProperties
import city.smartb.im.script.core.retryOnThrow
import city.smartb.im.script.init.ImInitScript
import city.smartb.im.script.space.config.SpaceConfigScript
import city.smartb.im.script.space.create.SpaceCreateScript
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class ScriptServiceRunner(
    private val context: ConfigurableApplicationContext,
    private val imInitScript: ImInitScript,
    private val imRetryProperties: ImRetryProperties,
    private val spaceConfigService: SpaceConfigScript,
    private val spaceCreateScript: SpaceCreateScript
): CommandLineRunner {

    private val logger = LoggerFactory.getLogger(ScriptServiceRunner::class.java)

    override fun run(vararg args: String?) = runBlocking {
        try {
            runScript("Init", imInitScript::run)
            runScript("Realm-Create", spaceCreateScript::run)
            runScript("Realm-Config", spaceConfigService::run)
        } catch (_: RuntimeException) {
        } finally {
            context.close()
        }
    }

    @Suppress("TooGenericExceptionThrown")
    suspend fun runScript(script: String, block: suspend () -> Unit) {
        val success = retryOnThrow(
            actionName = script,
            maxRetries = imRetryProperties.max,
            retryDelayMillis = imRetryProperties.delayMillis,
            logger = logger
        ) {
            block()
        }

        if (!success) {
            throw RuntimeException()
        }
    }
}
