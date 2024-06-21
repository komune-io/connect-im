package io.komune.im.infra.keycloak.client

import f2.client.ktor.http.plugin.model.AuthRealm
import f2.client.ktor.http.plugin.model.RealmId
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
    val defaultRealmRole = "default-roles-$realmId"

	/* Client */
	fun clients(): ClientsResource {
		return realm(realmId).clients()
	}

    fun client(id: String): ClientResource {
        return realm(realmId).clients().get(id)!!
    }

	fun getClientByIdentifier(identifier: String): ClientRepresentation? {
		return realm(realmId).clients().findByClientId(identifier)?.firstOrNull()
	}

	/* User */
	fun users(): UsersResource {
		return realm(realmId).users()
	}

	fun user(id: String): UserResource {
		return  realm(realmId).users().get(id)
	}

	/* Role */
	fun roles(): RolesResource {
		return  realm(realmId).roles()
	}

	fun role(identifier: String): RoleResource {
		return  realm(realmId).roles().get(identifier)
	}

	/* Group */
	fun groups(): GroupsResource {
		return realm(realmId).groups()
	}

	fun group(id: String): GroupResource {
		return realm(realmId).groups().group(id)
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
