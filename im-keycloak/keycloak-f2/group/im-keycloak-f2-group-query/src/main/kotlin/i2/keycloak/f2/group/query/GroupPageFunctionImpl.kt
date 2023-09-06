package i2.keycloak.f2.group.query

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageResult
import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.f2.group.query.model.asModel
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.ws.rs.NotFoundException

@Configuration
class GroupPageFunctionImpl{

	private val logger = LoggerFactory.getLogger(GroupPageFunctionImpl::class.java)

	@Bean
	fun groupPageFunction(): GroupPageFunction = keycloakF2Function { cmd, client ->
		try {
			val roles = client.roles().list().associate { role ->
				val composites = client.role(role.name).realmRoleComposites.mapNotNull { it.name }
				role.name to composites.toList()
			}

			var groups = client.groups()
				.groups("", 0, Int.MAX_VALUE, false)
				.asSequence()
				.map { group -> group.asModel { roles[it].orEmpty() } }

			if (!cmd.withDisabled) {
				groups = groups.filter(GroupModel::enabled)
			}

			cmd.search?.let { searchFilter ->
				groups = groups.filter { group -> group.name.contains(searchFilter, true) }
			}

			cmd.groupId?.let { groupId ->
				groups = groups.filter { group -> group.id == groupId }
			}

			cmd.roles?.let { roleFilter ->
				if(roleFilter.isNotEmpty()) {
					groups = groups.filter { group -> roleFilter.any { it in group.roles.effectiveRoles } }
				}
			}

			cmd.attributes.forEach { (key, value) ->
				groups = groups.filter { group -> value == group.attributes[key] }
			}

			groups = groups.sortedByDescending { it.attributes["creationDate"]?.toLong() ?: 0 }

			val count = groups.count()

			val groupsPage = if (cmd.page.size != null && cmd.page.page != null) {
				groups.chunked(cmd.page.size!!).elementAtOrNull(cmd.page.page!!).orEmpty()
			} else {
				groups.toList()
			}

			GroupPageResult(
				page = Page(
					total = count,
					items = groupsPage
				)
			)
		} catch (e: NotFoundException) {
			GroupPageResult(Page(0, emptyList()))
		} catch (e: Exception) {
			val msg = "Error fetching Groups"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
