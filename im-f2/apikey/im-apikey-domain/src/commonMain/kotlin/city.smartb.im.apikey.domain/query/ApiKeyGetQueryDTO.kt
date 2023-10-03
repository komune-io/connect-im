package city.smartb.im.apikey.domain.query

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyDTO
import city.smartb.im.apikey.domain.model.ApiKeyId
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

/**
 * @d2 query
 * @parent [ApiKeyGetFunction]
 */
@JsExport
@JsName("ApiKeyGetQueryDTO")
interface ApiKeyGetQueryDTO: Query {
    /**
     * Id of the API key to get.
     */
    val id: ApiKeyId
}

/**
 * @d2 inherit
 */
data class ApiKeyGetQuery(
    override val id: ApiKeyId
): ApiKeyGetQueryDTO

/**
 * @d2 result
 * @parent [ApiKeyGetFunction]
 */
@JsExport
@JsName("ApiKeyGetResultDTO")
interface ApiKeyGetResultDTO: Event {
    /**
     * The API key matching the id, or null if it does not exist.
     */
    val item: ApiKeyDTO?
}

/**
 * @d2 inherit
 */
data class ApiKeyGetResult(
    override val item: ApiKey?
): ApiKeyGetResultDTO
