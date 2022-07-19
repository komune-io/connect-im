package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function

/**
 * Update the logo of a user.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 60
 */
typealias UserUploadLogoFunction = F2Function<UserUploadLogoCommand, UserUploadedLogoEvent>

/**
 * @d2 command
 * @parent [UserUploadLogoFunction]
 */
data class UserUploadLogoCommand(
    /**
     * Identifier of the user.
     */
    val id: UserId
): Command

/**
 * @d2 event
 * @parent [UserUploadLogoFunction]
 */
data class UserUploadedLogoEvent(
    /**
     * Identifier of the user.
     */
    val id: UserId,

    /**
     * Public URL of the newly uploaded logo
     */
    val url: String
): Event
