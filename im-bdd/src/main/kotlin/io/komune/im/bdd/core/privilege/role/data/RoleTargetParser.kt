package io.komune.im.bdd.core.privilege.role.data

import io.komune.im.core.privilege.domain.model.RoleTarget
import s2.bdd.data.parser.EntryParser
import kotlin.reflect.jvm.jvmName

private val roleTargetParser = EntryParser(
    parseErrorMessage = "Role target must be in ${RoleTarget::class.jvmName} values",
    parser = RoleTarget::valueOf
)

fun Map<String, String>.extractRoleTarget(key: String) = roleTargetParser.single(this, key)
fun Map<String, String>.extractRoleTargetList(key: String) = roleTargetParser.list(this, key)
