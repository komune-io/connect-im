package city.smartb.im.apikey.domain.features.query

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.commons.model.OrganizationId
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get an apikey by ID.
 * @d2 function
 * @parent [city.smartb.im.apikey.domain.D2ApiKeyPage]
 * @order 10
 */
typealias ApiKeyGetFunction = F2Function<ApiKeyGetQuery, ApiKeyGetResult>

@JsExport
@JsName("ApiKeyGetQueryDTO")
interface ApiKeyGetQueryDTO: Query {
    val id: ApiKeyId
    val organizationId: OrganizationId
}

/**
 * @d2 query
 * @parent [ApiKeyGetFunction]
 */
data class ApiKeyGetQuery(
    /**
     * Identifier of the apikey.
     */
    override val id: ApiKeyId,
    /**
     * Identifier of the organizationId.
     */
    override val organizationId: OrganizationId
): ApiKeyGetQueryDTO

@JsExport
@JsName("ApiKeyGetResultDTO")
interface ApiKeyGetResultDTO: Event {
    val item: ApiKeyDTO?
}

/**
 * @d2 result
 * @parent [ApiKeyGetFunction]
 */
data class ApiKeyGetResult(
    /**
     * The apikey.
     */
    override val item: ApiKey?
): ApiKeyGetResultDTO
