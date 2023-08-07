package i2.keycloak.f2.role.query

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.role.domain.features.query.RoleGetByIdQueryFunction
import i2.keycloak.f2.role.domain.features.query.RoleGetByIdResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RoleGetByIdFunctionImpl {
    private val logger = LoggerFactory.getLogger(RoleGetByIdFunctionImpl::class.java)

    @Bean
    fun i2RoleGetById(): RoleGetByIdQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getRoleResource(cmd.id)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByIdResult)
        } catch (e: NotFoundException) {
            RoleGetByIdResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with id[${cmd.id}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }
}
