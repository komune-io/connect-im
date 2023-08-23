package im.script.function.core.service

import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.query.RealmGetFunction
import i2.keycloak.f2.realm.domain.features.query.RealmGetQuery
import i2.keycloak.f2.role.domain.Role
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQuery
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.stereotype.Service

@Service
class ScriptFinderService(
    private val realmGetFunction: RealmGetFunction,
    private val clientGetByClientIdentifierQueryFunction: ClientGetByClientIdentifierFunction,
    private val roleGetByNameQueryFunction: RoleGetByNameQueryFunction,
    private val userGetByEmailQueryFunction: UserGetByEmailFunction,
) {

    suspend fun getRealm(authRealm: AuthRealm, realmId: RealmId): RealmModel? {
        return  RealmGetQuery(
            id = realmId,
            authRealm = authRealm
        ).invokeWith(realmGetFunction).item
    }

    suspend fun getClient(authRealm: AuthRealm, realmId: RealmId, id: ClientIdentifier): ClientModel? {
        return ClientGetByClientIdentifierQuery(
            clientIdentifier = id,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(clientGetByClientIdentifierQueryFunction).item
    }

    suspend fun getRole(authRealm: AuthRealm, name: RoleName, realmId: String): Role? {
        return RoleGetByNameQuery(
            name = name,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(roleGetByNameQueryFunction).item
    }

    suspend fun getUser(authRealm: AuthRealm, email: String, realmId: RealmId): UserModel? {
        return UserGetByEmailQuery(
            email = email,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(userGetByEmailQueryFunction).item
    }
}
