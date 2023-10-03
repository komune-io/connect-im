package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyModel
import city.smartb.im.commons.Transformer
import city.smartb.im.commons.utils.mapAsyncDeferred
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import kotlinx.coroutines.awaitAll

class ApiKeyToDTOTransformer(
    private val privilegeFinderService: PrivilegeFinderService,
): Transformer<ApiKeyModel, ApiKey>() {

    override suspend fun transform(item: ApiKeyModel): ApiKey {
        val roles = item.roles.mapAsyncDeferred(privilegeFinderService::getRole)
        return ApiKey(
            id = item.id,
            identifier = item.identifier,
            name = item.name,
            roles = roles.awaitAll(),
            creationDate = item.creationDate,
        )
    }
}
