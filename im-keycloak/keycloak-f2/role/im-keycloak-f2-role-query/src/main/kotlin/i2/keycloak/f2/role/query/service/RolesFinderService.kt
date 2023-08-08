package i2.keycloak.f2.role.query.service

import i2.keycloak.f2.role.domain.RoleCompositesModel
import i2.keycloak.f2.role.domain.RolesCompositesModel
import i2.keycloak.f2.role.domain.defaultRealmRole
import i2.keycloak.f2.role.domain.features.query.RoleCompositeObjType
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
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
    suspend fun getAllRolesComposition(
        realmId: RealmId,
        client: AuthRealmClient
    ): List<RoleCompositesModel> = withContext(Dispatchers.IO) {
        client.roles().list().map { role ->
            async {  val composites = client.getRoleResource(role.name).realmRoleComposites.mapNotNull { it.name }
                RoleCompositesModel(
                    assignedRole = role.name,
                    effectiveRoles = composites
                ) }
        }.awaitAll()
    }

    suspend fun getRolesComposite(
        realmId: RealmId,
        objId: String,
        objType: RoleCompositeObjType,
        client: AuthRealmClient
    ): RolesCompositesModel {
        val roleResource = when(objType) {
            RoleCompositeObjType.USER ->  client.getUserResource(realmId, objId).roles().realmLevel()
            RoleCompositeObjType.GROUP ->  client.getGroupResource(realmId, objId).roles().realmLevel()
        }
        return roleResource.fetchAsyncRoles(realmId)
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
