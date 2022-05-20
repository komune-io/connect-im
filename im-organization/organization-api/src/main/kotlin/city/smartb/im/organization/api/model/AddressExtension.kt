package city.smartb.im.organization.api.model

import city.smartb.im.commons.model.AddressBase

fun AddressBase?.orEmpty() = this ?: AddressBase(
    street = "",
    postalCode = "",
    city = ""
)
