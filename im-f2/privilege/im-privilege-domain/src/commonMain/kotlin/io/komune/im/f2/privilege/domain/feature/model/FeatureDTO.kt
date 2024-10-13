package io.komune.im.f2.privilege.domain.feature.model

import io.komune.im.commons.model.FeatureId
import io.komune.im.commons.model.FeatureIdentifier
import io.komune.im.core.privilege.domain.model.PrivilegeType
import io.komune.im.f2.privilege.domain.model.PrivilegeDTO
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

/**
 * Feature flag that represents a group of actions within a system
 * and can be used to enable or disable the related permissions.
 * @d2 model
 * @parent [io.komune.im.f2.privilege.domain.D2FeaturePage]
 * @order 10
 */
@JsExport
interface FeatureDTO: PrivilegeDTO {
    /**
     * Generated id of the feature.
     */
    override val id: FeatureId

    /**
     * @ref [PrivilegeDTO.type]
     * @example "FEATURE"
     */
    override val type: String

    /**
     * Identifier of the feature. Must be unique within a realm.
     * @example "feat_organization"
     */
    override val identifier: FeatureIdentifier

    /**
     * Description of the feature.
     * @example "Organization management features"
     */
    override val description: String
}

/**
 * @d2 inherit
 */
@Serializable
data class Feature(
    override val id: FeatureId,
    override val identifier: FeatureIdentifier,
    override val description: String
): FeatureDTO {
    override val type = PrivilegeType.FEATURE.name
}
