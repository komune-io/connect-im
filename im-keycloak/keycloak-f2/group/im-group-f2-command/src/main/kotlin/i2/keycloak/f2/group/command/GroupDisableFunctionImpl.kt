package i2.keycloak.f2.group.command

import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.group.domain.features.command.GroupDisableFunction
import i2.keycloak.f2.group.domain.features.command.GroupDisabledEvent
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesCommand
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupDisableFunctionImpl(
	private val groupSetAttributesFunction: GroupSetAttributesFunction
) {

	@Bean
	fun groupDisableFunction(): GroupDisableFunction = f2Function { cmd ->
		GroupSetAttributesCommand(
			id = cmd.id,
			attributes = mapOf(GroupModel::enabled.name to "false"),
			realmId = cmd.realmId,
			auth = cmd.auth
		).invokeWith(groupSetAttributesFunction)

		GroupDisabledEvent(
			id = cmd.id
		)
	}
}
