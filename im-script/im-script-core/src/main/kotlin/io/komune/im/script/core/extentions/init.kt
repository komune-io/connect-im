package io.komune.im.script.core.extentions

import org.slf4j.Logger

suspend fun <T> init(logPrefix: String, logger: Logger, find: suspend () -> T?, create: suspend () -> T): T {
    logger.info("$logPrefix...")
    val found = find()
    if(found != null) {
        logger.info("$logPrefix already exists")
        return found
    }

    return create().also {
        logger.info("$logPrefix created")
    }

}
