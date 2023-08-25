package i2.keycloak.f2.realm.command

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.realm.domain.features.command.RealmDeleteCommand
import i2.keycloak.f2.realm.domain.features.command.RealmDeleteFunction
import i2.keycloak.f2.realm.domain.features.command.RealmDeletedEvent
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import i2.keycloak.realm.client.config.realmsResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmDeleteFunctionImpl {

	@Bean
	fun realmDeleteFunction(): RealmDeleteFunction = f2Function { cmd ->
		val masterRealm = AuthRealmClientBuilder().build(cmd.masterRealmAuth)
		masterRealm.deleteRealm(cmd)
		RealmDeletedEvent(cmd.id)
	}

	private fun AuthRealmClient.deleteRealm(cmd: RealmDeleteCommand) {
		realmsResource().realm(cmd.id).remove()
	}
}
