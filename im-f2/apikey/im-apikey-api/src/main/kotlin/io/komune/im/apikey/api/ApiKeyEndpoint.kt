package io.komune.im.apikey.api

import io.komune.im.apikey.domain.command.ApiKeyOrganizationAddFunction
import io.komune.im.apikey.domain.command.ApikeyRemoveFunction
import io.komune.im.apikey.domain.query.ApiKeyGetFunction
import io.komune.im.apikey.domain.query.ApiKeyGetResult
import io.komune.im.apikey.domain.query.ApiKeyPageFunction
import io.komune.im.apikey.lib.ApiKeyAggregateService
import io.komune.im.apikey.lib.ApiKeyFinderService
import io.komune.im.commons.auth.policies.f2Function
import f2.dsl.cqrs.page.OffsetPagination
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger

/**
 * @d2 service
 * @parent [io.komune.im.apikey.domain.D2ApikeyPage]
 */
@RestController
@RequestMapping
@Service
class ApiKeyEndpoint(
    private val apiKeyAggregateService: ApiKeyAggregateService,
    private val apiKeyFinderService: ApiKeyFinderService,
    private val apikeyPoliciesEnforcer: ApiKeyPoliciesEnforcer,
) {
    private val logger by Logger()

    /**
     * Fetch an Apikey by its ID.
     */
    @Bean
    fun apiKeyGet(): ApiKeyGetFunction = f2Function { query ->
        logger.info("apiKeyGet: $query")
        apikeyPoliciesEnforcer.checkGet(query.id)
        apiKeyFinderService.getOrNull(query.id).let(::ApiKeyGetResult)
    }

    /**
     * Fetch a page of apikeys.
     */
    @Bean
    fun apiKeyPage(): ApiKeyPageFunction = f2Function { query ->
        logger.info("apiKeyPage: $query")
        apikeyPoliciesEnforcer.checkPage()

        val enforcedQuery = apikeyPoliciesEnforcer.enforcePage(query)
        apiKeyFinderService.page(
            search = enforcedQuery.search,
            organizationId = enforcedQuery.organizationId,
            role = enforcedQuery.role,
            attributes = enforcedQuery.attributes,
            withDisabled = enforcedQuery.withDisabled,
            offset = OffsetPagination(
                offset = enforcedQuery.offset ?: 0,
                limit = enforcedQuery.limit ?: Int.MAX_VALUE
            )
        )
    }


    /**
     * Create an apikey.
     */
    @Bean
    fun apiKeyCreate(): ApiKeyOrganizationAddFunction = f2Function { command ->
        logger.info("apiKeyCreate: $command")
        apikeyPoliciesEnforcer.checkCreate()
        apiKeyAggregateService.create(command)
    }


    /**
     * Remove an API key from an apiKey.
     */
    @Bean
    fun apiKeyRemove(): ApikeyRemoveFunction = f2Function { command ->
        logger.info("apiKeyRemove: $command")
        apikeyPoliciesEnforcer.checkRemove(command.id)
        apiKeyAggregateService.remove(command)
    }



}
