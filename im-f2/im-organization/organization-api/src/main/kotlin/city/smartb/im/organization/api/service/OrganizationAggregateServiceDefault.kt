package city.smartb.im.organization.api.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.lib.service.ApiKeyAggregateService
import city.smartb.im.infra.redis.RedisCache
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.service.OrganizationAggregateService
import city.smartb.im.user.lib.service.UserAggregateService
import city.smartb.im.user.lib.service.UserFinderService
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientDeleteFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetServiceAccountFunction
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupDeleteFunction
import i2.keycloak.f2.group.domain.features.command.GroupDisableFunction
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import i2.keycloak.f2.user.domain.features.command.UserSetAttributesFunction
import org.springframework.stereotype.Service

@Service
class OrganizationAggregateServiceDefault(
    authenticationResolver: ImAuthenticationProvider,
    clientCreateFunction: ClientCreateFunction,
    clientDeleteFunction: ClientDeleteFunction,
    clientGetServiceAccountFunction: ClientGetServiceAccountFunction,
    clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    groupCreateFunction: GroupCreateFunction,
    groupDeleteFunction: GroupDeleteFunction,
    groupDisableFunction: GroupDisableFunction,
    groupSetAttributesFunction: GroupSetAttributesFunction,
    groupUpdateFunction: GroupUpdateFunction,
    organizationFinderService: OrganizationFinderServiceDefault,
    userAggregateService: UserAggregateService,
    userFinderService: UserFinderService,
    userSetAttributesFunction: UserSetAttributesFunction,
    redisCache: RedisCache,
    apiKeyService: ApiKeyAggregateService<ApiKey>,
): OrganizationAggregateService<Organization>(
    authenticationResolver = authenticationResolver,
    clientCreateFunction = clientCreateFunction,
    clientDeleteFunction = clientDeleteFunction,
    clientGetServiceAccountFunction = clientGetServiceAccountFunction,
    clientServiceAccountRolesGrantFunction = clientServiceAccountRolesGrantFunction,
    groupCreateFunction = groupCreateFunction,
    groupDeleteFunction = groupDeleteFunction,
    groupDisableFunction = groupDisableFunction,
    groupSetAttributesFunction = groupSetAttributesFunction,
    groupUpdateFunction = groupUpdateFunction,
    organizationFinderService = organizationFinderService,
    userAggregateService = userAggregateService,
    userFinderService = userFinderService,
    userSetAttributesFunction = userSetAttributesFunction,
    redisCache = redisCache,
    apiKeyService = apiKeyService
)
