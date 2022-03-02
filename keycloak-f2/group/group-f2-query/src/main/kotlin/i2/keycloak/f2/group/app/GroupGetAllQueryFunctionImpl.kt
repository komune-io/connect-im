package i2.keycloak.f2.group.app

import f2.dsl.cqrs.page.Page
import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.app.model.asModel
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryResult
import i2.keycloak.realm.client.config.AuthRealmClient
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class GroupGetAllQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun groupGetAllQueryFunction(): GroupGetAllQueryFunction = keycloakF2Function { cmd, client ->
		try {
			val count = client.countGroups(cmd.realmId)
			var groups = client.groups(cmd.realmId).groups("", 0, count, false)

			if (cmd.name != null) {
				groups = groups.filter { group -> group.name.contains(cmd.name!!, true) }
			}

			if (cmd.role != null) {
				groups = groups.filter { group -> group.realmRoles.contains(cmd.role) }
			}

			if (cmd.page != null && cmd.size != null) {
				groups = groups.chunked(cmd.size!!)[cmd.page!!]
			}

			GroupGetAllQueryResult(
				groups = Page(
					total = count,
					list = groups.map(GroupRepresentation::asModel)
				)
			)
		} catch (e: NotFoundException) {
			GroupGetAllQueryResult(Page(0, emptyList()))
		} catch (e: Exception) {
			val msg = "Error fetching Groups"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun AuthRealmClient.countGroups(realmId: String): Int {
		return this.groups(realmId).count()["count"]!!.toInt()
	}
}
