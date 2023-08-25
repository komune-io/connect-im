package city.smartb.im.apikey.api

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.lib.service.ApiKeyMapper

class ApiKeyMapperImpl : ApiKeyMapper<ApiKey, ApiKey> {

    override fun mapModel(model: ApiKey): ApiKey {
        return model
    }

    override fun mapApiKey(model: ApiKey): ApiKey {
        return model
    }
}
