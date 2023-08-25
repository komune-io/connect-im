package im.script.function.config.service

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.f2.privilege.domain.role.model.RoleIdentifier
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.command.UserCreateCommand
import i2.keycloak.f2.user.domain.features.command.UserCreateFunction
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantCommand
import i2.keycloak.f2.user.domain.features.command.UserRolesGrantFunction
import i2.keycloak.f2.user.domain.model.UserId
import org.springframework.stereotype.Service

@Service
class ConfigService(
//    private val roleAddCompositesFunction: RoleAddCompositesFunction,
//    private val roleCreateFunction: RoleCreateFunction,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val userCreateFunction: UserCreateFunction,
    private val userRolesGrantFunction: UserRolesGrantFunction,
) {

    suspend fun createRole(authRealm: AuthRealm, name: RoleIdentifier): String {
//        return RoleCreateCommand(
//            name = name,
//            description = null,
//            isClientRole = false,
//            composites = emptyList(),
//            auth = authRealm,
//            realmId = authRealm.realmId
//        ).invokeWith(roleCreateFunction).id
        TODO()
    }

    suspend fun addRoleComposites(authRealm: AuthRealm, role: RoleIdentifier, composites: List<RoleIdentifier>): String {
//        return RoleAddCompositesCommand(
//            roleName = role,
//            composites = composites,
//            auth = authRealm,
//            realmId = authRealm.realmId
//        ).invokeWith(roleAddCompositesFunction).id
        TODO()
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

    suspend fun grantUser(authRealm: AuthRealm, id: UserId, vararg roles: RoleIdentifier) {
        UserRolesGrantCommand(
            id = id,
            roles = roles.toList(),
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(userRolesGrantFunction)
    }
}
