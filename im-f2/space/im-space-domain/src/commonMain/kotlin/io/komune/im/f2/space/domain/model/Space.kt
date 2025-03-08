package io.komune.im.f2.space.domain.model

import io.komune.im.commons.model.SpaceIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * Representation of a space.
 * @D2 model
 * @parent [io.komune.im.f2.space.domain.D2SpacePage]
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
    /**
     * Display name of the space.
     * @example "sb-dev"
     */
    val displayName: String?

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

@Serializable
data class Space(
    override val identifier: SpaceIdentifier,
    override val displayName: String?,
    override val theme: String?,
    override val smtp: Map<String, String>?,
    override val locales: List<String>?
): SpaceDTO
