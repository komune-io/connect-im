package i2.keycloak.f2.realm.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.realm.domain.features.command.RealmDeleteCommand
import i2.keycloak.f2.realm.domain.features.command.RealmDeleteFunction
import i2.keycloak.f2.realm.domain.features.command.RealmDeletedEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmDeleteFunctionImpl {

	@Bean
	fun realmDeleteFunction(): RealmDeleteFunction = f2Function { cmd ->
		val masterRealm = KeycloakClientBuilder.openConnection(cmd.masterRealmAuth).forAuthedRealm()
		masterRealm.deleteRealm(cmd)
		RealmDeletedEvent(cmd.id)
	}

	private fun KeycloakClient.deleteRealm(cmd: RealmDeleteCommand) {
		realm(cmd.id).remove()
	}
}
