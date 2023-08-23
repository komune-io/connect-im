package city.smartb.im.privilege.api.service

import city.smartb.im.infra.keycloak.client.KeycloakClientProvider
import city.smartb.im.privilege.api.model.toPrivilege
import city.smartb.im.privilege.domain.model.Privilege
import city.smartb.im.privilege.domain.model.PrivilegeIdentifier
import city.smartb.im.privilege.domain.permission.model.Permission
import city.smartb.im.privilege.domain.permission.model.PermissionIdentifier
import city.smartb.im.privilege.domain.role.model.Role
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import f2.spring.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PrivilegeFinderService(
    private val keycloakClientProvider: KeycloakClientProvider
) {

    private val logger by Logger()

    suspend fun getById(cmd: RoleGetByIdQuery): RoleGetByIdResult {
        val auth = authenticationResolver.getAuth()
        val realmClient = AuthRealmClientBuilder().build(auth)
        return try {
            realmClient.getRoleResource(cmd.id)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByIdResult)
        } catch (e: NotFoundException) {
            RoleGetByIdResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with id[${cmd.id}]"
            logger.error(msg, e)
            throw RoleFetchingError(msg).asException(e)
        }
    }

    suspend fun getByName(query: RoleGetByNameQuery): RoleGetByNameResult {
        val auth = authenticationResolver.getAuth()
        val realmClient = AuthRealmClientBuilder().build(auth)
        return try {
            realmClient.getRoleResource(auth.realmId, query.name)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByNameResult)
        } catch (e: NotFoundException) {
            RoleGetByNameResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with name[${query.name}]"
            logger.error(msg, e)
            throw RoleFetchingError(msg).asException(e)
        }
    }

    fun RoleRepresentation.asModel() = RoleModel(
        id = id,
        name = name,
        description = description,
        isClientRole = clientRole
    )
}
