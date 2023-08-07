package i2.app.core

import kotlinx.coroutines.delay
import org.slf4j.Logger

suspend fun retryWithExceptions(
    maxRetries: Int = 5,
    retryDelayMillis: Long = 10000,
    logger: Logger,
    action: suspend () -> Unit): Boolean {
    var success = false
    var attempts = 0

    while (attempts < maxRetries && !success) {
        try {
            logger.info("////////////////////////////////////////////////////")
            logger.info("Retrying action (attempt $attempts of ${maxRetries})")
            logger.info("////////////////////////////////////////////////////")
            action()
            success = true
        } catch (ex: Exception) {
            attempts++
            logger.error("Failed to execute action (attempt $attempts of ${maxRetries}). Retrying...", ex)

            if (attempts >= maxRetries) {
                logger.error("Failed to execute action after ${maxRetries} attempts.")
                break
            }

            delay(retryDelayMillis) // Wait for the specified time before retrying
        }
    }
    return success
}
