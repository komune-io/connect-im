package i2.keycloak.f2.user.query

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.role.domain.RoleCompositeObjType
import i2.keycloak.f2.role.domain.features.query.RoleCompositeGetFunction
import i2.keycloak.f2.role.domain.features.query.RoleCompositeGetQuery
import i2.keycloak.f2.user.domain.features.query.UserGetRolesFunction
import i2.keycloak.f2.user.domain.features.query.UserGetRolesResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetRolesFunctionImpl {

	@Bean
	fun userGetRolesQueryFunctionImpl(roleCompositeGetFunction: RoleCompositeGetFunction): UserGetRolesFunction = f2Function { query ->
		val response = RoleCompositeGetQuery(
			objId = query.userId,
			objType = RoleCompositeObjType.USER,
			auth = query.auth,
			realmId = query.realmId,
		).invokeWith(roleCompositeGetFunction)

		UserGetRolesResult(response.item)
	}

}
