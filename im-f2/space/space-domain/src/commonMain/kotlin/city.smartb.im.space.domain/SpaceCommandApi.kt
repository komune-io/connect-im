package city.smartb.im.space.domain

import city.smartb.im.space.domain.features.command.SpaceCreateFunction
import city.smartb.im.space.domain.features.command.SpaceDeleteFunction
import city.smartb.im.space.domain.features.query.SpaceGetFunction
import city.smartb.im.space.domain.features.query.SpacePageFunction

/**
 * @d2 api
 * @parent [D2SpacePage]
 */
interface SpaceApi: SpaceCommandApi, SpaceQueryApi

interface SpaceCommandApi {
    /** Create a space. */
    fun spaceCreate(): SpaceCreateFunction
    /** Delete a space. */
    fun spaceDelete(): SpaceDeleteFunction
}

interface SpaceQueryApi {
    /** Fetch a space by id. */
    fun spaceGet(): SpaceGetFunction
    /** Fetch a page of spaces. */
    fun spacePage(): SpacePageFunction
}
