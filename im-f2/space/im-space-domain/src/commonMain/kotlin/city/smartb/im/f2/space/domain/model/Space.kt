package city.smartb.im.f2.space.domain.model

import city.smartb.im.commons.model.SpaceIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Representation of a space.
 * @D2 model
 * @parent [city.smartb.im.f2.space.domain.D2SpacePage]
 * @order 10
 */
@JsExport
@JsName("SpaceDTO")
interface SpaceDTO {
    /**
     * Identifier of the space.
     * @example "sb-dev"
     */
    val identifier: SpaceIdentifier?

    val smtp: Map<String, String>?

    /**
     * Theme used by the space.
     * @example "im"
     */
    val theme: String?

    /**
     * Locales supported by the space.
     * @example [["en", "fr"]]
     */
    val locales: List<String>?
}

data class Space(
    override val identifier: SpaceIdentifier,
    override val theme: String?,
    override val smtp: Map<String, String>?,
    override val locales: List<String>?
): SpaceDTO
