package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.model.ApiKeyModel
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.core.organization.domain.model.OrganizationModel

const val ORGANIZATION_FIELD_API_KEYS = "apiKeys"
fun OrganizationModel.apiKeys(): List<ApiKeyModel> {
    return attributes[ORGANIZATION_FIELD_API_KEYS]?.parseJsonTo(Array<ApiKeyModel>::class.java).orEmpty()
}
