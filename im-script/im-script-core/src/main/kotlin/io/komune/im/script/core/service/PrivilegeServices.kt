package io.komune.im.script.core.service

import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import org.springframework.stereotype.Service

/** The privilege services a script writes and reads with. */
@Service
data class PrivilegeServices(
    val aggregate: PrivilegeAggregateService,
    val finder: PrivilegeFinderService,
)
