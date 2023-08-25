package city.smartb.im.space.domain

import city.smartb.im.space.domain.features.command.SpaceCreateFunction
import city.smartb.im.space.domain.features.command.SpaceDeleteFunction

/**
 * Represents a set of features related to space commands.
 *
 * This interface provides methods for creating and deleting spaces.
 */
interface SpaceCommandFeatures {
    /**
     * Create a space.
     */
    fun spaceCreate(): SpaceCreateFunction

    /**
     * Delete a space.
     */
    fun spaceDelete(): SpaceDeleteFunction
}
