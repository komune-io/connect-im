package i2.init.api.auth

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQuery
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.f2.realm.domain.features.query.RealmGetFunction
import i2.keycloak.f2.realm.domain.features.query.RealmGetQuery
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQuery
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class KeycloakFinderService(
    private val authRealm: AuthRealm,
    private val clientGetByClientIdentifierQueryFunction: ClientGetByClientIdentifierFunction,
    private val realmGetOneQueryFunction: RealmGetFunction,
    private val userGetByEmailQueryFunction: UserGetByEmailFunction,
    private val roleGetByNameQueryFunction: RoleGetByNameQueryFunction,
) {
    suspend fun getRealm(id: RealmId): RealmModel? {
        return RealmGetQuery(
            id = id,
            authRealm = authRealm
        ).invokeWith(realmGetOneQueryFunction).item
    }

    suspend fun getClient(id: ClientIdentifier, realmId: RealmId): ClientModel? {
        return ClientGetByClientIdentifierQuery(
            clientIdentifier = id,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(clientGetByClientIdentifierQueryFunction).item
    }

    suspend fun getUser(email: String, realmId: RealmId): UserModel? {
        return UserGetByEmailQuery(
            email = email,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(userGetByEmailQueryFunction).item
    }

    suspend fun getRole(name: RoleName, realm: RealmId): RoleModel? {
        return RoleGetByNameQuery(
            name = name,
            realmId = realm,
            auth = authRealm
        ).invokeWith(roleGetByNameQueryFunction).item
    }
}
