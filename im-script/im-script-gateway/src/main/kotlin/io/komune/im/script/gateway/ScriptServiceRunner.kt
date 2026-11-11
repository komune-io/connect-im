package io.komune.im.script.gateway

import io.komune.im.script.core.config.properties.ImRetryProperties
import io.komune.im.script.core.retryOnThrow
import io.komune.im.script.init.ImInitScript
import io.komune.im.script.space.config.SpaceConfigScript
import io.komune.im.script.space.create.SpaceCreateScript
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
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

    override fun run(vararg args: String) {
        runThenClose(
            context = context,
            scripts = listOf(
                "Init" to imInitScript::run,
                "Realm-Create" to spaceCreateScript::run,
                "Realm-Config" to spaceConfigService::run,
            ),
            retry = imRetryProperties,
            logger = logger,
        )
    }
}

/** Runs [scripts], then closes [context] whatever happened; a failed script leaves with its error. */
internal fun runThenClose(
    context: ConfigurableApplicationContext,
    scripts: List<Pair<String, suspend () -> Unit>>,
    retry: ImRetryProperties,
    logger: Logger,
) {
    try {
        runBlocking { runScripts(scripts = scripts, retry = retry, logger = logger) }
    } finally {
        context.close()
    }
}

/** Runs [scripts] in order, each retried as [retry] says; the first one that fails stops the run with its error. */
internal suspend fun runScripts(
    scripts: List<Pair<String, suspend () -> Unit>>,
    retry: ImRetryProperties,
    logger: Logger,
) {
    scripts.forEach { (script, block) ->
        val isSuccess = retryOnThrow(
            actionName = script,
            maxRetries = retry.max,
            retryDelayMillis = retry.delayMillis,
            logger = logger,
            action = block,
        )
        check(isSuccess) { "Failed to run script $script" }
    }
}
