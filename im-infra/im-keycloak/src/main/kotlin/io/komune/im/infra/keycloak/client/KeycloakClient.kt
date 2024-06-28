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
import org.slf4j.LoggerFactory

class KeycloakClient(
	val keycloak: Keycloak,
    val auth: AuthRealm,
    val realmId: RealmId
) {

    private val logger = LoggerFactory.getLogger(KeycloakClient::class.java)

    val defaultRealmRole = "default-roles-$realmId"

	/* Client */
	fun clients(): ClientsResource {
        log("clients")
		return realm(realmId).clients()
	}

    private fun log(invokeLog: String) {
        logger.debug("Invoke ${invokeLog} on realm[${realmId}] with auth[$auth]")
    }

    fun client(id: String): ClientResource {
        log("client[id=$id]")
        return realm(realmId).clients().get(id)!!
    }

	fun getClientByIdentifier(identifier: String): ClientRepresentation? {
        log("getClientByIdentifier[identifier=${identifier}]")
		return realm(realmId).clients().findByClientId(identifier)?.firstOrNull()
	}

	/* User */
	fun users(): UsersResource {
        log("users")
		return realm(realmId).users()
	}

	fun user(id: String): UserResource {
        log("user[id=$id]")
		return  realm(realmId).users().get(id)
	}

	/* Role */
	fun roles(): RolesResource {
        log("roles")
		return  realm(realmId).roles()
	}

	fun role(identifier: String): RoleResource {
        log("roles[id=$identifier]")
		return  realm(realmId).roles().get(identifier)
	}

	/* Group */
	fun groups(): GroupsResource {
        log("groups")
		return realm(realmId).groups()
	}

	fun group(id: String): GroupResource {
        log("group[id=$id]")
		return realm(realmId).groups().group(id)
	}

	/* Realm */
	fun realm(): RealmResource {
        log("realm")
		return keycloak.realm(realmId)
	}

	fun realm(id: String): RealmResource {
        log("realm[id=$id]")
		return keycloak.realm(id)
	}

    fun realms(): RealmsResource {
        log("realms")
        return keycloak.realms()
    }
}
