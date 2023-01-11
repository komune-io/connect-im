package city.smartb.im.organization.api.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.service.OrganizationAggregateService
import city.smartb.im.user.lib.service.UserAggregateService
import city.smartb.im.user.lib.service.UserFinderService
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupDeleteFunction
import i2.keycloak.f2.group.domain.features.command.GroupDisableFunction
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import org.springframework.stereotype.Service

@Service
class OrganizationAggregateServiceDefault(
    authenticationResolver: ImAuthenticationProvider,
    groupCreateFunction: GroupCreateFunction,
    groupDeleteFunction: GroupDeleteFunction,
    groupDisableFunction: GroupDisableFunction,
    groupSetAttributesFunction: GroupSetAttributesFunction,
    groupUpdateFunction: GroupUpdateFunction,
    organizationFinderService: OrganizationFinderServiceDefault,
    userAggregateService: UserAggregateService,
    userFinderService: UserFinderService,
    redisCache: RedisCache,
): OrganizationAggregateService<Organization>(
    authenticationResolver = authenticationResolver,
    groupCreateFunction = groupCreateFunction,
    groupDeleteFunction = groupDeleteFunction,
    groupDisableFunction = groupDisableFunction,
    groupSetAttributesFunction = groupSetAttributesFunction,
    groupUpdateFunction = groupUpdateFunction,
    organizationFinderService = organizationFinderService,
    userAggregateService = userAggregateService,
    userFinderService = userFinderService,
    redisCache = redisCache,
)
