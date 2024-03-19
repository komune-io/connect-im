package io.komune.im.apikey.lib.service

import io.komune.im.apikey.domain.model.ApiKey
import io.komune.im.apikey.domain.model.ApiKeyModel
import io.komune.im.commons.Transformer
import io.komune.im.commons.utils.mapAsyncDeferred
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
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
