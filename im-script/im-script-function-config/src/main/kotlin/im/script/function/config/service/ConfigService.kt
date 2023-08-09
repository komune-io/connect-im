package im.script.function.config.service

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantCommand
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantCommand
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.role.domain.RoleId
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesCommand
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesFunction
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.keycloak.f2.role.domain.features.command.RoleCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.features.command.UserCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import org.springframework.stereotype.Service

@Service
class ConfigService(
    private val clientCreateFunction: ClientCreateFunction,
    private val roleAddCompositesFunction: RoleAddCompositesFunction,
    private val roleCreateFunction: RoleCreateFunction,
    private val userCreateFunction: UserCreateFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
    private val clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    private val clientRealmManagementRolesGrantFunction: ClientRealmManagementRolesGrantFunction,
) {

    suspend fun createClient(
        authRealm: AuthRealm,
        identifier: ClientIdentifier,
        secret: String? = null,
        isPublic: Boolean = true,
        isDirectAccessGrantsEnabled: Boolean = true,
        isServiceAccountsEnabled: Boolean = true,
        authorizationServicesEnabled: Boolean = false,
        isStandardFlowEnabled: Boolean = false,
        baseUrl: String? = null,
        protocolMappers: Map<String, String> = emptyMap(),
    ): ClientId {
        return ClientCreateCommand(
            auth = authRealm,
            realmId = authRealm.realmId,
            clientIdentifier = identifier,
            secret = secret,
            isPublicClient = isPublic,
            isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = isServiceAccountsEnabled,
            authorizationServicesEnabled = authorizationServicesEnabled,
            isStandardFlowEnabled = isStandardFlowEnabled,
            rootUrl = baseUrl,
            redirectUris = listOfNotNull(baseUrl),
            baseUrl = baseUrl ?: "",
            adminUrl = baseUrl ?: "",
            webOrigins = listOfNotNull(baseUrl),
            protocolMappers = protocolMappers,
        ).invokeWith(clientCreateFunction).id
    }

    suspend fun createRole(authRealm: AuthRealm, name: RoleName): RoleId {
        return RoleCreateCommand(
            name = name,
            description = null,
            isClientRole = false,
            composites = emptyList(),
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(roleCreateFunction).id
    }

    suspend fun addRoleComposites(authRealm: AuthRealm, role: RoleName, composites: List<RoleName>): String {
        return RoleAddCompositesCommand(
            roleName = role,
            composites = composites,
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(roleAddCompositesFunction).id
    }

    suspend fun createUser(
        authRealm: AuthRealm,
        username: String,
        email: String,
        firstname: String,
        lastname: String,
        isEnable: Boolean,
        attributes: Map<String, String> = emptyMap(),
        password: String?
    ): UserId {
        return UserCreateCommand(
            username = username,
            email = email,
            firstname = firstname,
            lastname = lastname,
            isEnable = isEnable,
            isEmailVerified = true,
            attributes = attributes,
            auth = authRealm,
            realmId = authRealm.realmId,
            password = password
        ).invokeWith(userCreateFunction).id
    }

    suspend fun grantUser(authRealm: AuthRealm, id: UserId, vararg roles: RoleName) {
        UserRolesGrantCommand(
            id = id,
            roles = roles.toList(),
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(userRolesGrantFunction)
    }

    suspend fun grantClient(authRealm: AuthRealm, id: ClientId, roles: List<RoleName>) {
        ClientServiceAccountRolesGrantCommand(
            id = id,
            roles = roles,
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(clientServiceAccountRolesGrantFunction)
    }

    suspend fun grantRealmManagementClient(authRealm: AuthRealm, id: ClientId, roles: List<RoleName>) {
        ClientRealmManagementRolesGrantCommand(
            id = id,
            roles = roles,
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(clientRealmManagementRolesGrantFunction)
    }
}
