package city.smartb.im.apikey.api.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.lib.service.ApiKeyAggregateService
import city.smartb.im.apikey.lib.service.ApiKeyFinderService
import city.smartb.im.infra.redis.RedisCache
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientDeleteFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetServiceAccountFunction
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.springframework.stereotype.Service

@Service
class ApiKeyAggregateServiceDefault(
    authenticationResolver: ImAuthenticationProvider,
    clientCreateFunction: ClientCreateFunction,
    clientDeleteFunction: ClientDeleteFunction,
    clientGetServiceAccountFunction: ClientGetServiceAccountFunction,
    clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    apikeyFinderService: ApiKeyFinderService<ApiKey>,
    userSetAttributesFunction: UserSetAttributesFunction,
    redisCache: RedisCache,
    groupSetAttributesFunction: GroupSetAttributesFunction,
    groupGetFunction: GroupGetFunction,
): ApiKeyAggregateService<ApiKey>(
    authenticationResolver = authenticationResolver,
    clientCreateFunction = clientCreateFunction,
    clientDeleteFunction = clientDeleteFunction,
    clientGetServiceAccountFunction = clientGetServiceAccountFunction,
    clientServiceAccountRolesGrantFunction = clientServiceAccountRolesGrantFunction,
    apikeyFinderService = apikeyFinderService,
    userSetAttributesFunction = userSetAttributesFunction,
    redisCache = redisCache,
    groupSetAttributesFunction = groupSetAttributesFunction,
    groupGetFunction = groupGetFunction,
)
