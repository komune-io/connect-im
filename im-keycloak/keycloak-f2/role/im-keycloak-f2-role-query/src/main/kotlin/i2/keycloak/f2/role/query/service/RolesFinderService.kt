package i2.keycloak.f2.role.query.service

import city.smartb.im.commons.model.RealmId
import city.smartb.im.infra.keycloak.client.KeycloakClient
import i2.keycloak.f2.role.domain.RoleCompositeObjType
import i2.keycloak.f2.role.domain.RoleCompositesModel
import i2.keycloak.f2.role.domain.RolesCompositesModel
import i2.keycloak.f2.role.domain.defaultRealmRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.keycloak.admin.client.resource.RoleScopeResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Service

@Service
class RolesFinderService{
    suspend fun getAllRolesComposition(client: KeycloakClient): List<RoleCompositesModel> = withContext(Dispatchers.IO) {
        client.roles().list().map { role ->
            async {  val composites = client.role(role.name).realmRoleComposites.mapNotNull { it.name }
                RoleCompositesModel(
                    assignedRole = role.name,
                    effectiveRoles = composites
                ) }
        }.awaitAll()
    }

    suspend fun getRolesComposite(
        objId: String,
        objType: RoleCompositeObjType,
        client: KeycloakClient
    ): RolesCompositesModel {
        val roleResource = when (objType) {
            RoleCompositeObjType.USER ->  client.user(objId).roles().realmLevel()
            RoleCompositeObjType.GROUP ->  client.group(objId).roles().realmLevel()
        }
        return roleResource.fetchAsyncRoles(client.realmId)
    }

    private suspend fun RoleScopeResource.fetchAsyncRoles(realmId: RealmId): RolesCompositesModel = withContext(Dispatchers.IO) {
        val assignedRoles = async { rolesListAll() }
        val effectiveRoles = async { rolesListEffective() }
        RolesCompositesModel(
            assignedRoles = assignedRoles.await().toList() - defaultRealmRole(realmId),
            effectiveRoles = effectiveRoles.await().toList()
        )
    }

    private suspend fun RoleScopeResource.rolesListEffective() = flow {
        listEffective()
            .map(RoleRepresentation::getName)
            .forEach { role -> emit(role) }
    }

    private suspend fun RoleScopeResource.rolesListAll(): Flow<String> = flow {
        listAll()
            .map(RoleRepresentation::getName)
            .forEach { role ->
                emit(role)
            }
    }
}
