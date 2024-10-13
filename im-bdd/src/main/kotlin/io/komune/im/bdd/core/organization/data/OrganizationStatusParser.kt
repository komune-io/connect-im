package io.komune.im.bdd.core.organization.data

import io.komune.im.f2.organization.domain.model.OrganizationStatus
import org.springframework.context.annotation.Configuration
import s2.bdd.data.parser.EntryParser

@Configuration
class OrganizationStatusParser: EntryParser<OrganizationStatus>(
    output = OrganizationStatus::class,
    parseErrorMessage = "Expected ${OrganizationStatus::class.simpleName} value",
    parser = OrganizationStatus::valueOf
)
