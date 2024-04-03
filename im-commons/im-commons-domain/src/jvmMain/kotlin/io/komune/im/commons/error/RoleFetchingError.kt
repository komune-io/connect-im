package io.komune.im.commons.error

import f2.dsl.cqrs.error.F2Error
import java.util.UUID

class RoleFetchingError(message: String): F2Error(
    id = UUID.randomUUID().toString(),
    message = message,
    code = 1,
    timestamp = System.currentTimeMillis().toString(),
    requestId = null
)
