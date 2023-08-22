package im.script.function.init.service

import city.smartb.im.infra.keycloak.AuthRealm
import city.smartb.im.infra.keycloak.RealmId
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.ClientId
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
import org.springframework.stereotype.Service

@Service
class InitService(
    private val realmCreateFunction: RealmCreateFunction,
    private val userCreateFunction: UserCreateFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
    private val roleCreateFunction: RoleCreateFunction,
    private val roleAddCompositesFunction: RoleAddCompositesFunction
) {

    suspend fun createRealm(authRealm: AuthRealm, id: RealmId, theme: String?, smtpConfig: Map<String, String>?): RealmId {
        return RealmCreateCommand(
            id = id,
            theme = theme,
            locale = null,
            smtpServer = smtpConfig,
            masterRealmAuth = authRealm,
        ).invokeWith(realmCreateFunction).id
    }

    suspend fun createUser(
        authRealm: AuthRealm,
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

    suspend fun grantUser(authRealm: AuthRealm, id: UserId, realm: String, clientId: ClientId?, vararg roles: RoleName) {
        UserRolesGrantCommand(
            id = id,
            roles = roles.toList(),
            auth = authRealm,
            realmId = realm,
            clientId = clientId
        ).invokeWith(userRolesGrantFunction)
    }

    suspend fun createRole(authRealm: AuthRealm, roleName: RoleName, description: String?, composites: List<String>, realm: String) {
        RoleCreateCommand(
            name = roleName,
            description = description,
            isClientRole = false,
            composites = composites,
            auth = authRealm,
            realmId = realm
        ).invokeWith(roleCreateFunction)
    }

    suspend fun roleAddComposites(authRealm: AuthRealm, roleName: RoleName, composites: List<String>, realm: String) {
        RoleAddCompositesCommand(
            roleName = roleName,
            composites = composites,
            auth = authRealm,
            realmId = realm
        ).invokeWith(roleAddCompositesFunction)
    }
}
