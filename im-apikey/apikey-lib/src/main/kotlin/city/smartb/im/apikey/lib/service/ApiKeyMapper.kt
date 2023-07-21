package city.smartb.im.apikey.lib.service

interface ApiKeyMapper<FROM, ApiKey> {
    fun mapModel(model: FROM): ApiKey
    fun mapApiKey(model: ApiKey): FROM
}
