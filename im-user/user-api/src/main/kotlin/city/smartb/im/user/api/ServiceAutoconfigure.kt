package city.smartb.im.user.api

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.user.api.service.UserTransformer
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceAutoconfigure {

	@Bean
	fun userTransformer(
		authenticationResolver: ImAuthenticationProvider,
		userGetGroupsFunction: UserGetGroupsFunction
	): UserTransformer {
		return UserTransformer(authenticationResolver, userGetGroupsFunction)
	}
}
