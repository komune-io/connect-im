package city.smartb.im.commons.utils

import city.smartb.im.commons.model.Address

fun Address?.orEmpty() = this ?: Address(
    street = "",
    postalCode = "",
    city = ""
)
