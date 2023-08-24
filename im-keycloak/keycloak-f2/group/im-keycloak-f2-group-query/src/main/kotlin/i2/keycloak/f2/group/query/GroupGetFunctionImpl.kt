package i2.keycloak.f2.group.query

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetResult
import i2.keycloak.f2.group.query.model.asModel
import i2.keycloak.f2.role.domain.features.query.RoleCompositeGetFunction
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.NotFoundException

@Configuration
class GroupGetFunctionImpl {

	private val logger = LoggerFactory.getLogger(GroupGetFunctionImpl::class.java)

	@Bean
	fun groupGetFunction(roleCompositeGetFunction: RoleCompositeGetFunction): GroupGetFunction = keycloakF2Function { cmd, client ->
		try {
			val roles = client.roles().list().associate { role ->
				val composites = client.role(role.name).realmRoleComposites.mapNotNull { it.name }
				role.name to composites.toList()
			}
//			val response = RoleCompositeGetQuery(
//				objId = cmd.id,
//				objType = RoleCompositeObjType.USER,
//				auth = cmd.auth,
//				realmId = cmd.realmId,
//			).invokeWith(roleCompositeGetFunction)
			client.group(cmd.id)
				.toRepresentation()
				.asModel{ roles[it].orEmpty().toList() }
				.let(::GroupGetResult)
		} catch (e: NotFoundException) {
			GroupGetResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching Group with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}
}
