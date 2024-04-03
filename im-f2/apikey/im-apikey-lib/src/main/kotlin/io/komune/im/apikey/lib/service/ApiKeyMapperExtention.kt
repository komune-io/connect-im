package io.komune.im.apikey.lib.service

import io.komune.im.apikey.domain.model.ApiKeyModel
import io.komune.im.commons.utils.parseJsonTo
import io.komune.im.core.organization.domain.model.OrganizationModel

const val ORGANIZATION_FIELD_API_KEYS = "apiKeys"
fun OrganizationModel.apiKeys(): List<ApiKeyModel> {
    return attributes[ORGANIZATION_FIELD_API_KEYS]?.parseJsonTo(Array<ApiKeyModel>::class.java).orEmpty()
}
