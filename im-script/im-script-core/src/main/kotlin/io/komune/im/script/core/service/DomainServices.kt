package io.komune.im.script.core.service

import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import io.komune.im.f2.space.lib.SpaceAggregateService
import io.komune.im.f2.space.lib.SpaceFinderService
import io.komune.im.f2.user.lib.UserAggregateService
import io.komune.im.f2.user.lib.UserFinderService
import org.springframework.stereotype.Service

/** The privilege services a script writes and reads with. */
@Service
class PrivilegeServices(
    val aggregate: PrivilegeAggregateService,
    val finder: PrivilegeFinderService,
)

/** The space services a script writes and reads with. */
@Service
class SpaceServices(
    val aggregate: SpaceAggregateService,
    val finder: SpaceFinderService,
)

/** The user services a script writes and reads with. */
@Service
class UserServices(
    val aggregate: UserAggregateService,
    val finder: UserFinderService,
)
