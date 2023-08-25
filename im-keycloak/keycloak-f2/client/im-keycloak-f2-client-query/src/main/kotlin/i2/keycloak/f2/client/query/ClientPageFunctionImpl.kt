package i2.keycloak.f2.client.query

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientPageFunction
import i2.keycloak.f2.client.domain.features.query.ClientPageResult
import i2.keycloak.f2.commons.app.keycloakF2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientPageFunctionImpl {

	companion object {
		const val PAGE_SIZE = 10
		const val PAGE_NUMBER = 1
	}

	@Bean
	fun clientPageFunction(): ClientPageFunction = keycloakF2Function { cmd, keycloakClient ->
		val size = cmd.page.size ?: PAGE_SIZE
		val page = cmd.page.page ?: PAGE_NUMBER
 		val clients = keycloakClient.clients().findAll()

		clients.chunked(size)
			.getOrElse(page) { emptyList() }
			.asModels()
			.asResult(clients.size)
	}

	private fun List<ClientModel>.asResult(total: Int): ClientPageResult {
		return ClientPageResult(
			Page(
				total = total,
				items = this
			)
		)
	}
}
