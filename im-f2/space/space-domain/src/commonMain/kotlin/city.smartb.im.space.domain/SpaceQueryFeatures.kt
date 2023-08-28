package city.smartb.im.space.domain

import city.smartb.im.space.domain.features.query.SpaceGetFunction
import city.smartb.im.space.domain.features.query.SpacePageFunction
import city.smartb.im.space.domain.model.SpaceDTO

interface SpaceQueryFeatures {
    /**
     * Fetch a space by its ID.
     */
    fun spaceGet(): SpaceGetFunction
    /**
     * Fetch a page of spaces.
     */
    fun spacePage(): SpacePageFunction
}
