package i2.keycloak.f2.user.query

import city.smartb.im.infra.keycloak.client.KeycloakClient
import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.domain.features.query.UserPageFunction
import i2.keycloak.f2.user.domain.features.query.UserPageResult
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.f2.user.query.model.asModels
import i2.keycloak.f2.user.query.service.UserFinderService
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserPageFunctionImpl {

	@Bean
	fun userPageFunction(userFinderService: UserFinderService): UserPageFunction = keycloakF2Function { query, client ->
		val userRepresentations = if (query.groupId == null) {
			listUsers(client)
		} else {
			listUsersOfGroup(client, query.groupId!!)
		}

		var users = userRepresentations.asModels { userId ->
			userFinderService.getRolesComposition(userId, client)
		}.asSequence()

		if (!query.withDisabled) {
			users = users.filter(UserModel::enabled)
		}

		query.search?.split(" ")
			?.map(String::trim)
			?.forEach { searchWord ->
				users = users.filter { user ->
					user.email.orEmpty().contains(searchWord, true) ||
							user.firstName.orEmpty().contains(searchWord, true) ||
							user.lastName.orEmpty().contains(searchWord, true)
				}
			}

		query.roles?.let { roleFilter ->
			if(roleFilter.isNotEmpty()) {
				users = users.filter { user ->
					roleFilter.any { it in user.roles.assignedRoles }
				}
			}
		}

		query.attributes.forEach { (key, value) ->
			users = users.filter { user -> value == user.attributes[key] }
		}

		users = users.sortedByDescending(UserModel::creationDate)

		val count = users.count()
		val usersPage = if (query.page.size != null && query.page.page != null) {
			users.chunked(query.page.size!!).elementAtOrNull(query.page.page!!).orEmpty()
		} else {
			users.toList()
		}

		UserPageResult(
			Page(
				total = count,
				items = usersPage
			)
		)
	}

	private fun listUsers(client: KeycloakClient): List<UserRepresentation> {
		return client.users().list()
	}

	private fun listUsersOfGroup(client: KeycloakClient, groupId: GroupId): List<UserRepresentation> {
		return client.group(groupId).members()
	}
}
