package i2.keycloak.f2.role.query

import f2.dsl.cqrs.page.Page
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.features.query.RolePageFunction
import i2.keycloak.f2.role.domain.features.query.RolePageResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RolePageFunctionImpl {

	companion object {
		const val PAGE_SIZE = 10
		const val PAGE_NUMBER = 1
	}

	@Bean
	fun i2RolePageFunction(): RolePageFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)

		val size = cmd.page.size ?: PAGE_SIZE
		val page = cmd.page.page ?: PAGE_NUMBER
		val roles = realmClient.roles(cmd.realmId).list()

		roles.chunked(size)
			.getOrElse(page) { emptyList() }
			.asModels()
			.asResult(roles.size)
	}

	private fun List<RoleModel>.asResult(total: Int): RolePageResult {
		return RolePageResult(
			Page(
				total = total,
				items = this,
			)
		)
	}
}
