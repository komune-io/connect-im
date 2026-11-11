package io.komune.im.script.core

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import org.slf4j.Logger

suspend fun retryOnThrow(
    actionName: String,
    maxRetries: Int = 5,
    retryDelayMillis: Long = 10_000,
    logger: Logger,
    action: suspend () -> Unit
): Boolean {
    var isSuccess = false
    var attempts = 0
    @Suppress("TooGenericExceptionCaught")
    while (attempts < maxRetries && !isSuccess) {
        attempts++
        try {
            logger.info("////////////////////////////////////////////////////")
            logger.info("$actionName (attempt $attempts of $maxRetries)")
            logger.info("////////////////////////////////////////////////////")
            action()
            isSuccess = true
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Exception) {
            logger.error("$actionName Failed to execute (attempt $attempts of $maxRetries). Retrying...", ex)

            if (attempts >= maxRetries) {
                logger.error("$actionName Failed to execute after $maxRetries attempts. Exiting application.")
                break
            }

            delay(retryDelayMillis) // Wait for the specified time before retrying
        }
    }
    return isSuccess
}

suspend fun <T> init(logPrefix: String, logger: Logger, find: suspend () -> T?, create: suspend () -> T): T {
    val found = find()

    if (found != null) {
        logger.info("$logPrefix already exists")
        return found
    }

    return create().also {
        logger.info("$logPrefix created")
    }
}
