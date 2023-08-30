package city.smartb.im.space.domain.model

import city.smartb.im.commons.model.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * @d2 hidden
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias SpaceId = RealmId

@JsExport
@JsName("SpaceDTO")
interface SpaceDTO {
    val identifier: SpaceId?
}

/**
 * Representation of a space.
 * @D2 model
 * @parent [city.smartb.im.space.domain.D2SpacePage]
 * @order 10
 */
data class Space(
    /**
     * Identifier of the space.
     * @example "SmartB"
     */
    override val identifier: SpaceId,
): SpaceDTO
