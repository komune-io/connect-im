package city.smartb.im.script.space.create.service

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.features.command.UserCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import org.springframework.stereotype.Service

@Service
class InitService(
    private val userCreateFunction: UserCreateFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
) {

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

    suspend fun grantUser(authRealm: AuthRealm, id: UserId, realm: String, clientId: ClientId?, vararg roles: RoleIdentifier) {
        UserRolesGrantCommand(
            id = id,
            roles = roles.toList(),
            auth = authRealm,
            realmId = realm,
            clientId = clientId
        ).invokeWith(userRolesGrantFunction)
    }
}
