package city.smartb.im.commons.model

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Representation of the address.
 * @d2 model
 * @parent [city.smartb.im.commons.D2AddressPage]
 * @title Model
 */
@JsExport
@JsName("AddressDTO")
interface AddressDTO {
    /**
     * The street address.
     * @example "2 Rue du pavillon"
     */
    val street: String
    /**
     * The postal code.
     * @example "34090"
     */
    val postalCode: String
    /**
     * The locality in which the street address is.
     * @example "Montpellier"
     */
    val city: String
}

data class Address(
    override val street: String,
    override val postalCode: String,
    override val city: String
): AddressDTO
