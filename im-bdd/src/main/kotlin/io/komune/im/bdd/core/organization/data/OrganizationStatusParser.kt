package io.komune.im.bdd.core.organization.data

import io.komune.im.f2.organization.domain.model.OrganizationStatus
import s2.bdd.data.parser.EntryParser
import kotlin.reflect.jvm.jvmName

private val organizationStatusParser = EntryParser(
    parseErrorMessage = "Organization status must be in ${OrganizationStatus::class.jvmName} values",
    parser = OrganizationStatus::valueOf
)

fun Map<String, String>.extractOrganizationStatus(key: String) = organizationStatusParser.single(this, key)
fun Map<String, String>.extractOrganizationStatusList(key: String) = organizationStatusParser.list(this, key)
