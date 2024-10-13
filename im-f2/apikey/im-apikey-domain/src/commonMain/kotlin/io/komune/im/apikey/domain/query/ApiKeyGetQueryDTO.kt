package io.komune.im.apikey.domain.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyDTO
import io.komune.im.apikey.domain.model.ApiKeyId
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Get an apikey by ID.
 * @d2 function
 * @parent [io.komune.im.apikey.domain.D2ApiKeyPage]
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
@Serializable
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
@Serializable
data class ApiKeyGetResult(
    override val item: ApiKey?
): ApiKeyGetResultDTO
