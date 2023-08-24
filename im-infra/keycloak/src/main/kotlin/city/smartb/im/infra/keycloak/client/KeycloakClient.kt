package city.smartb.im.infra.keycloak.client

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.ClientResource
import org.keycloak.admin.client.resource.ClientsResource
import org.keycloak.admin.client.resource.GroupResource
import org.keycloak.admin.client.resource.GroupsResource
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.RealmsResource
import org.keycloak.admin.client.resource.RoleResource
import org.keycloak.admin.client.resource.RolesResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.idm.ClientRepresentation

class KeycloakClient(
	val keycloak: Keycloak,
    val auth: AuthRealm,
    val realmId: RealmId
) {

	/* Client */

	fun clients(): ClientsResource {
		return realm().clients()
	}

    fun client(id: String): ClientResource {
        return clients().get(id)!!
    }

	fun getClientByClientId(clientId: String): ClientRepresentation? {
		return clients().findByClientId(clientId)?.firstOrNull()
	}

	/* User */

	fun users(): UsersResource {
		return realm().users()
	}

	fun user(id: String): UserResource {
		return users().get(id)
	}

	/* Role */

	fun roles(): RolesResource {
		return realm().roles()
	}

	fun role(identifier: String): RoleResource {
		return roles().get(identifier)
	}

	/* Group */

	fun groups(): GroupsResource {
		return realm().groups()
	}

	fun group(id: String): GroupResource {
		return groups().group(id)
	}

	/* Realm */

	fun realm(): RealmResource {
		return keycloak.realm(realmId)
	}

	fun realm(id: String): RealmResource {
		return keycloak.realm(id)
	}

    fun realms(): RealmsResource {
        return keycloak.realms()
    }
}
