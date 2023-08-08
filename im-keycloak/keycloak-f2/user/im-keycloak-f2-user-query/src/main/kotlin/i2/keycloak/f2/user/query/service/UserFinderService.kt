package i2.keycloak.f2.user.query.service

import i2.keycloak.f2.role.domain.RolesCompositesModel
import i2.keycloak.f2.role.domain.features.query.RoleCompositeObjType
import i2.keycloak.f2.role.query.service.RolesFinderService
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val rolesFinderService: RolesFinderService
) {

    suspend fun getAllRolesComposition(
        realmId: RealmId,
        client: AuthRealmClient
    ) = rolesFinderService.getAllRolesComposition(realmId, client)

    suspend fun getRolesComposition(userId: UserId, realmId: RealmId, client: AuthRealmClient): RolesCompositesModel {
        return rolesFinderService.getRolesComposite(realmId = realmId, objId = userId, objType = RoleCompositeObjType.USER, client)
    }
}
