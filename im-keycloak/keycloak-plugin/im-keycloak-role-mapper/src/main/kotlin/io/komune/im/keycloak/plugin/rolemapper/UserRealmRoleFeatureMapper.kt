package io.komune.im.keycloak.plugin.rolemapper

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import java.util.stream.Collectors
import org.keycloak.models.ClientSessionContext
import org.keycloak.models.KeycloakSession
import org.keycloak.models.ProtocolMapperModel
import org.keycloak.models.RoleModel
import org.keycloak.models.UserSessionModel
import org.keycloak.protocol.ProtocolMapperUtils
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper
import org.keycloak.provider.ProviderConfigProperty
import org.keycloak.representations.IDToken

class UserRealmRoleFeatureMapper : AbstractOIDCProtocolMapper(), OIDCAccessTokenMapper, OIDCIDTokenMapper,
    UserInfoTokenMapper {

    companion object {
        const val PROVIDER_ID = KeycloakPluginIds.MAPPER_REALM_ROLE_FEATURE

        private val CONFIG_PROPERTIES = mutableListOf(
            ProviderConfigProperty().also {
                it.name = ProtocolMapperUtils.MULTIVALUED
                it.label = ProtocolMapperUtils.MULTIVALUED_LABEL
                it.helpText = ProtocolMapperUtils.MULTIVALUED_HELP_TEXT
                it.type = ProviderConfigProperty.BOOLEAN_TYPE
                it.defaultValue = "true"
            }
        ).also { OIDCAttributeMapperHelper.addAttributeConfig(it, UserRealmRoleFeatureMapper::class.java) }
    }

    override fun getId() = PROVIDER_ID
    override fun getHelpText() = "Map user realm roles to a token claim, filtered by feature flags."
    override fun getDisplayCategory() = TOKEN_MAPPER_CATEGORY
    override fun getDisplayType() = "User Realm Featured Role"
    override fun getConfigProperties() = CONFIG_PROPERTIES
    override fun getPriority() = 100

    override fun setClaim(
        token: IDToken,
        mappingModel: ProtocolMapperModel,
        userSession: UserSessionModel,
        keycloakSession: KeycloakSession,
        clientSessionCtx: ClientSessionContext
    ) {
        val features = clientSessionCtx.rolesStream
            .filter { "FEATURE" in it.attributes?.get("type").orEmpty() }
            .map { it.name }
            .collect(Collectors.toSet()) as Set<String>

        val userAssignedRoles = userSession.user.realmRoleMappingsStream.collect(Collectors.toList())
        val groupAssignedRoles =
            userSession.user.groupsStream.flatMap { it.realmRoleMappingsStream }.collect(Collectors.toList())

        val visitedRoles = mutableSetOf<String>()
        val remainingRoles = mutableListOf<RoleModel>()
        remainingRoles.addAll(userAssignedRoles)
        remainingRoles.addAll(groupAssignedRoles)

        val actualRoles = mutableListOf<String>()

        while (remainingRoles.isNotEmpty()) {
            val role = remainingRoles.removeFirst()
            if (role.name in visitedRoles) {
                continue
            }
            visitedRoles.add(role.name)

            if (role.isFeatured(features)) {
                actualRoles.add(role.name)
            }

            remainingRoles.addAll(role.compositesStream.collect(Collectors.toList()))
        }

        OIDCAttributeMapperHelper.mapClaim(token, mappingModel, actualRoles)
    }

    private fun RoleModel.isFeatured(features: Collection<String>): Boolean {
        val roleFeatureRules = attributes?.get("features")
            ?.map { it.parseJsonTo(Array<String>::class.java) }

        if (roleFeatureRules.isNullOrEmpty()) {
            return true
        }

        return roleFeatureRules.any {
            it.all { feature -> feature in features }
        }
    }
}
