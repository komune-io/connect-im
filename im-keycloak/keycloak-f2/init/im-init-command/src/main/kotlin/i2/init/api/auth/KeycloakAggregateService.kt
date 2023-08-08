package i2.init.api.auth

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantCommand
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantFunction
import i2.keycloak.f2.realm.domain.features.command.RealmCreateCommand
import i2.keycloak.f2.realm.domain.features.command.RealmCreateFunction
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
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class KeycloakAggregateService(
    private val authRealm: AuthRealm,
    private val clientCreateFunction: ClientCreateFunction,
    private val realmCreateFunction: RealmCreateFunction,
    private val userCreateFunction: UserCreateFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
    private val clientRealmManagementRolesGrantFunction: ClientRealmManagementRolesGrantFunction,
    private val roleCreateFunction: RoleCreateFunction,
    private val roleAddCompositesFunction: RoleAddCompositesFunction
) {

    suspend fun createRealm(id: RealmId, theme: String?, smtpConfig: Map<String, String>): RealmId {
        return RealmCreateCommand(
            id = id,
            theme = theme,
            locale = null,
            smtpServer = smtpConfig,
            masterRealmAuth = authRealm,
        ).invokeWith(realmCreateFunction).id
    }

    suspend fun createClient(
        identifier: ClientIdentifier,
        realm: String,
        secret: String? = null,
        isPublic: Boolean = true,
        isDirectAccessGrantsEnabled: Boolean = true,
        isServiceAccountsEnabled: Boolean = true,
        authorizationServicesEnabled: Boolean = false,
        isStandardFlowEnabled: Boolean = false,
        baseUrl: String? = null,
        localhostUrl: String? = null,
        protocolMappers: Map<String, String> = emptyMap(),
    ): ClientId {
        return ClientCreateCommand(
            auth = authRealm,
            realmId = realm,
            clientIdentifier = identifier,
            secret = secret,
            isPublicClient = isPublic,
            isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = isServiceAccountsEnabled,
            authorizationServicesEnabled = authorizationServicesEnabled,
            isStandardFlowEnabled = isStandardFlowEnabled,
            rootUrl = baseUrl,
            redirectUris = listOfNotNull(baseUrl, localhostUrl),
            baseUrl = baseUrl ?: "",
            adminUrl = baseUrl ?: "",
            webOrigins = listOfNotNull(baseUrl, localhostUrl),
            protocolMappers = protocolMappers,
        ).invokeWith(clientCreateFunction).id
    }

    suspend fun createUser(
        username: String,
        email: String,
        firstname: String,
        lastname: String,
        isEnable: Boolean,
        attributes: Map<String, String> = emptyMap(),
        password: String,
        realm: String
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
            realmId = realm,
            password = password
        ).invokeWith(userCreateFunction).id
    }

    suspend fun grantUser(id: UserId, realm: String, clientId: ClientId?, vararg roles: RoleName) {
        UserRolesGrantCommand(
            id = id,
            roles = roles.toList(),
            auth = authRealm,
            realmId = realm,
            clientId = clientId
        ).invokeWith(userRolesGrantFunction)
    }

    suspend fun grantClient(id: ClientId, realm: String, roles: List<RoleName>) {
        ClientRealmManagementRolesGrantCommand(
            id = id,
            roles = roles,
            auth = authRealm,
            realmId = realm
        ).invokeWith(clientRealmManagementRolesGrantFunction)
    }

    suspend fun createRole(roleName: RoleName, description: String?, composites: List<String>, realm: String) {
        RoleCreateCommand(
            name = roleName,
            description = description,
            isClientRole = false,
            composites = composites,
            auth = authRealm,
            realmId = realm
        ).invokeWith(roleCreateFunction)
    }

    suspend fun roleAddComposites(roleName: RoleName, composites: List<String>, realm: String) {
        RoleAddCompositesCommand(
            roleName = roleName,
            composites = composites,
            auth = authRealm,
            realmId = realm
        ).invokeWith(roleAddCompositesFunction)
    }
}
