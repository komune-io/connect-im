package city.smartb.im.space.domain.model

import i2.keycloak.f2.group.domain.model.GroupId
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Unique identifier of a space.
 * @d2 model
 * @parent [city.smartb.im.space.domain.D2SpacePage]
 * @order 20
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias SpaceId = GroupId

@JsExport
@JsName("SpaceDTO")
interface SpaceDTO {
    val id: SpaceId
    val name: String?
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
     */
    override val id: SpaceId,

    /**
     * Official name of the space.
     * @example "SmartB"
     */
    override val name: String?,

): SpaceDTO
