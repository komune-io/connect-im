package io.komune.im.script.core.service

import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import org.springframework.stereotype.Service

/** The user services a script writes and reads with. */
@Service
data class UserServices(
    val aggregate: UserAggregateService,
    val finder: UserFinderService,
)
