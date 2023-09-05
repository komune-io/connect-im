package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.commons.utils.parseJsonTo
import i2.keycloak.f2.group.domain.model.GroupModel

const val GROUP_API_KEYS_FIELD = "apiKeys"
fun GroupModel.toApiKeys(): List<ApiKey> {
    return attributes[GROUP_API_KEYS_FIELD]?.parseJsonTo(Array<ApiKey>::class.java).orEmpty()
}
