package city.smartb.im.f2.space.domain.model

import city.smartb.im.commons.model.SpaceIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("SpaceDTO")
interface SpaceDTO {
    val identifier: SpaceIdentifier?
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
    override val identifier: SpaceIdentifier,
): SpaceDTO
