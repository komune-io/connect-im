package io.komune.im.script.space.config

import io.komune.im.apikey.lib.ApiKeyAggregateService
import io.komune.im.f2.organization.lib.OrganizationAggregateService
import io.komune.im.f2.organization.lib.OrganizationFinderService
import org.springframework.stereotype.Service

/** The organization services the space configuration writes and reads with, API keys included. */
@Service
data class OrganizationServices(
    val aggregate: OrganizationAggregateService,
    val finder: OrganizationFinderService,
    val apiKeys: ApiKeyAggregateService,
)
