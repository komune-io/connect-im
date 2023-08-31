package i2.keycloak.f2.role.query

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.features.query.RoleCompositeGetFunction
import i2.keycloak.f2.role.domain.features.query.RoleCompositeGetResult
import i2.keycloak.f2.role.query.service.RolesFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RolesCompositeGetFunctionImpl {

	@Bean
	fun i2RolesCompositeGetFunctionImpl(rolesFinderService: RolesFinderService): RoleCompositeGetFunction = keycloakF2Function { cmd, client ->
		rolesFinderService.getRolesComposite(
			objId = cmd.objId,
			objType = cmd.objType,
            client = client
        ).let(::RoleCompositeGetResult)
	}

}
