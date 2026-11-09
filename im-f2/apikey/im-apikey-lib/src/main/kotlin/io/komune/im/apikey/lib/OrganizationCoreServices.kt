package io.komune.im.apikey.lib

import io.komune.im.core.organization.api.OrganizationCoreAggregateService
import io.komune.im.core.organization.api.OrganizationCoreFinderService
import org.springframework.stereotype.Service

/** The organization core services API keys are stored with. */
@Service
class OrganizationCoreServices(
    val aggregate: OrganizationCoreAggregateService,
    val finder: OrganizationCoreFinderService,
)
