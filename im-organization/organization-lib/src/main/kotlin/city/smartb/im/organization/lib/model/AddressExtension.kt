package city.smartb.im.organization.lib.model

import city.smartb.im.commons.model.Address

fun Address?.orEmpty() = this ?: Address(
    street = "",
    postalCode = "",
    city = ""
)
