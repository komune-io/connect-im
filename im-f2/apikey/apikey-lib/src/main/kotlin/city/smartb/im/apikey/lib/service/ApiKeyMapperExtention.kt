package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.core.organization.domain.model.Organization

const val GROUP_API_KEYS_FIELD = "apiKeys"
fun Organization.apiKeys(): List<ApiKey> {
    return attributes[GROUP_API_KEYS_FIELD]?.parseJsonTo(Array<ApiKey>::class.java).orEmpty()
}
