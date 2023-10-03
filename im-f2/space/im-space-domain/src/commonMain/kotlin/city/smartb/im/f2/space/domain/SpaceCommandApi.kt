package city.smartb.im.f2.space.domain

import city.smartb.im.f2.space.domain.command.SpaceDefineFunction
import city.smartb.im.f2.space.domain.command.SpaceDeleteFunction
import city.smartb.im.f2.space.domain.query.SpaceGetFunction
import city.smartb.im.f2.space.domain.query.SpacePageFunction

/**
 * @d2 api
 * @parent [D2SpacePage]
 */
interface SpaceApi: SpaceCommandApi, SpaceQueryApi

interface SpaceCommandApi {
    /** Create or update a space. */
    fun spaceDefine(): SpaceDefineFunction
    /** Delete a space. */
    fun spaceDelete(): SpaceDeleteFunction
}

interface SpaceQueryApi {
    /** Fetch a space by id. */
    fun spaceGet(): SpaceGetFunction
    /** Fetch a page of spaces. */
    fun spacePage(): SpacePageFunction
}
