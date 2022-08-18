package city.smartb.im.user.domain.features.command

import city.smartb.im.user.domain.model.UserId
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Update the logo of a user.
 * @d2 function
 * @parent [city.smartb.im.user.domain.D2UserPage]
 * @order 60
 */
typealias UserUploadLogoFunction = F2Function<UserUploadLogoCommand, UserUploadedLogoEvent>

@JsExport
@JsName("UserUploadLogoCommandDTO")
interface UserUploadLogoCommandDTO: Command {
    /**
     * Identifier of the user.
     */
    val id: UserId
}

/**
 * @d2 command
 * @parent [UserUploadLogoFunction]
 */
data class UserUploadLogoCommand(
    override val id: UserId
): UserUploadLogoCommandDTO

@JsExport
@JsName("UserUploadedLogoEventDTO")
interface UserUploadedLogoEventDTO: Event {
    /**
     * Identifier of the user.
     */
    val id: UserId

    /**
     * Public URL of the newly uploaded logo
     */
    val url: String
}

/**
 * @d2 event
 * @parent [UserUploadLogoFunction]
 */
data class UserUploadedLogoEvent(
    override val id: UserId,
    override val url: String
): UserUploadedLogoEventDTO
