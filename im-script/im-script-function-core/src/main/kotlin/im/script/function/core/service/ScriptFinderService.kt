package im.script.function.core.service

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailQuery
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.stereotype.Service

@Service
class ScriptFinderService(
    private val userGetByEmailQueryFunction: UserGetByEmailFunction,
) {
    suspend fun getUser(authRealm: AuthRealm, email: String, realmId: RealmId): UserModel? {
        return UserGetByEmailQuery(
            email = email,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(userGetByEmailQueryFunction).item
    }
}
