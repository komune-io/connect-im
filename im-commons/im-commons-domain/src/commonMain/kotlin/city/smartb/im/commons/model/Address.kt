package city.smartb.im.commons.model

/**
 * Representation of the address.
 * @d2 model
 * @parent [city.smartb.im.commons.D2AddressPage]
 * @title Model
 */
interface Address {
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

data class AddressBase(
    override val street: String,
    override val postalCode: String,
    override val city: String
): Address
