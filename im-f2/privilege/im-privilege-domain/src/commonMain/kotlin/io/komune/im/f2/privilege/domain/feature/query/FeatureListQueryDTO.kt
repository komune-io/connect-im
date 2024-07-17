package io.komune.im.f2.privilege.domain.feature.query

import f2.dsl.fnc.F2Function
import io.komune.im.f2.privilege.domain.feature.model.Feature
import io.komune.im.f2.privilege.domain.feature.model.FeatureDTO
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Get a list of features.
 * @d2 function
 * @parent [io.komune.im.f2.privilege.domain.D2FeaturePage]
 * @order 20
 */
typealias FeatureListFunction = F2Function<FeatureListQuery, FeatureListResult>

/**
 * @d2 query
 * @parent [FeatureListFunction]
 */
@JsExport
@JsName("FeatureListQueryDTO")
interface FeatureListQueryDTO

/**
 * @d2 inherit
 */
@Serializable
class FeatureListQuery: FeatureListQueryDTO

/**
 * @d2 result
 * @parent [FeatureListFunction]
 */
@JsExport
@JsName("FeatureListResultDTO")
interface FeatureListResultDTO {
    /**
     * Features matching the filters.
     */
    val items: List<FeatureDTO>
}

/**
 * @d2 inherit
 */
@Serializable
data class FeatureListResult(
    override val items: List<Feature>
): FeatureListResultDTO
