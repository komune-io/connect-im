package io.komune.im.bdd.core.privilege.role.data

import io.komune.im.core.privilege.domain.model.RoleTarget
import org.springframework.context.annotation.Configuration
import s2.bdd.data.parser.EntryParser

@Configuration
class RoleTargetParser: EntryParser<RoleTarget>(
    output = RoleTarget::class,
    parseErrorMessage = "Expected ${RoleTarget::class.simpleName} value",
    parser = RoleTarget::valueOf
)
