package io.komune.im.f2.privilege.domain.feature.query

import f2.dsl.fnc.F2Function
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.f2.privilege.domain.feature.model.Feature
import io.komune.im.f2.privilege.domain.feature.model.FeatureDTO
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a feature by identifier.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2FeaturePage]
 * @order 10
 */
typealias FeatureGetFunction = F2Function<FeatureGetQuery, FeatureGetResult>

/**
 * @d2 query
 * @parent [FeatureGetFunction]
 */
@JsExport
@JsName("FeatureGetQueryDTO")
interface FeatureGetQueryDTO {
    /**
     * Identifier of the feature to get.
     * @example [io.komune.im.f2.privilege.domain.feature.model.Feature.identifier]
     */
    val identifier: FeatureIdentifier
}

@Serializable
data class FeatureGetQuery(
    override val identifier: FeatureIdentifier
): FeatureGetQueryDTO

/**
 * @d2 result
 * @parent [FeatureGetFunction]
 */
@JsExport
@JsName("FeatureGetResultDTO")
interface FeatureGetResultDTO {
    /**
     * Feature matching the given identifier, or null if not found
     */
    val item: FeatureDTO?
}

/**
 * @d2 inherit
 */
@Serializable
data class FeatureGetResult(
    override val item: Feature?
): FeatureGetResultDTO
