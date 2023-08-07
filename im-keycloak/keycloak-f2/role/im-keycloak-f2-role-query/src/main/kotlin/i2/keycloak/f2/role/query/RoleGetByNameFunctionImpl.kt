package i2.keycloak.f2.role.query

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameQueryFunction
import i2.keycloak.f2.role.domain.features.query.RoleGetByNameResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleGetByNameFunctionImpl {
    private val logger = LoggerFactory.getLogger(RoleGetByNameFunctionImpl::class.java)

    @Bean
    fun i2RoleGetByNameFunction(): RoleGetByNameQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getRoleResource(cmd.realmId, cmd.name)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByNameResult)
        } catch (e: NotFoundException) {
            RoleGetByNameResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with name[${cmd.name}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }
}
