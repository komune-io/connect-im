package city.smartb.im.role.api.service

import city.smartb.im.api.config.bean.ImAuthenticationResolver
import city.smartb.im.role.domain.features.query.RoleGetByIdQuery
import city.smartb.im.role.domain.features.query.RoleGetByIdResult
import city.smartb.im.role.domain.features.query.RoleGetByNameQuery
import city.smartb.im.role.domain.features.query.RoleGetByNameResult
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class RoleFinderService(
    private val authenticationResolver: ImAuthenticationResolver,
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
            //TODO Change By IM ERROR AND CREATE IM-ERROR
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }

    @Bean
    suspend fun getByName(query: RoleGetByNameQuery): RoleGetByNameResult {
        val auth = authenticationResolver.getAuth()
        val realmClient = AuthRealmClientBuilder().build(auth)
        return try {
            realmClient.getRoleResource(query.realmId, query.name)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByNameResult)
        } catch (e: NotFoundException) {
            RoleGetByNameResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with name[${query.name}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }

    fun RoleRepresentation.asModel() = RoleModel(
        id = id,
        name = name,
        description = description,
        isClientRole = clientRole
    )

}