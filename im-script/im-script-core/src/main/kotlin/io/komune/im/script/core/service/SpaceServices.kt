package io.komune.im.script.core.service

import io.komune.im.f2.space.lib.SpaceAggregateService
import io.komune.im.f2.space.lib.SpaceFinderService
import org.springframework.stereotype.Service

/** The space services a script writes and reads with. */
@Service
data class SpaceServices(
    val aggregate: SpaceAggregateService,
    val finder: SpaceFinderService,
)
