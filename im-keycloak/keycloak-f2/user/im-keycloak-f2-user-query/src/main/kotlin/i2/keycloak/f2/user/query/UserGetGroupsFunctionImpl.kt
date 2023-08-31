package i2.keycloak.f2.user.query

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsResult
import i2.keycloak.f2.user.domain.model.UserGroup
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetGroupsFunctionImpl {

	@Bean
	fun userGetGroupsQueryFunction(): UserGetGroupsFunction = keycloakF2Function { query, client ->
		val items = client.user(query.userId).groups().map {
			val group = client.group(it.id).toRepresentation()
			UserGroup(group.id, group.name, group.realmRoles)
		}

		UserGetGroupsResult(
			items
		)
	}
}
