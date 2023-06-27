package city.smartb.im.apikey.api.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.apikey.lib.service.ApiKeyFinderService
import city.smartb.im.organization.lib.service.GroupMapper
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import org.springframework.stereotype.Service

@Service
class ApiKeyFinderServiceDefault(
    groupGetFunction: GroupGetFunction,
    groupPageFunction: GroupPageFunction,
    authenticationResolver: ImAuthenticationProvider,
    groupMapper: GroupMapper,
    redisCache: RedisCache,
): ApiKeyFinderService<ApiKey>(
    groupGetFunction = groupGetFunction,
    groupPageFunction = groupPageFunction,
    authenticationResolver = authenticationResolver,
    groupMapper = groupMapper,
    redisCache = redisCache
)
