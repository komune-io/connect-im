package i2.keycloak.f2.user.query.service

import city.smartb.im.commons.model.UserId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import i2.keycloak.f2.role.domain.RoleCompositeObjType
import i2.keycloak.f2.role.domain.RolesCompositesModel
import i2.keycloak.f2.role.query.service.RolesFinderService
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val rolesFinderService: RolesFinderService
) {

    suspend fun getAllRolesComposition(client: KeycloakClient) = rolesFinderService.getAllRolesComposition(client)

    suspend fun getRolesComposition(userId: UserId, client: KeycloakClient): RolesCompositesModel {
        return rolesFinderService.getRolesComposite(objId = userId, objType = RoleCompositeObjType.USER, client)
    }
}
