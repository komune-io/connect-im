package city.smartb.im.organization.api.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.service.GroupMapper
import city.smartb.im.organization.lib.service.InseeHttpClient
import city.smartb.im.organization.lib.service.OrganizationFinderService
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import org.springframework.stereotype.Service

@Service
class OrganizationFinderServiceDefault(
    inseeHttpClient: InseeHttpClient?,
    groupGetFunction: GroupGetFunction,
    groupPageFunction: GroupPageFunction,
    authenticationResolver: ImAuthenticationProvider,
    groupMapper: GroupMapper,
    redisCache: RedisCache,
): OrganizationFinderService<Organization>(
    inseeHttpClient = inseeHttpClient,
    groupGetFunction = groupGetFunction,
    groupPageFunction = groupPageFunction,
    authenticationResolver = authenticationResolver,
    groupMapper = groupMapper,
    redisCache = redisCache
)
