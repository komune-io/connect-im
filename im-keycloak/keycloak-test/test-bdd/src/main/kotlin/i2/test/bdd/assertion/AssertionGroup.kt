package i2.test.bdd.assertion

import city.smartb.im.commons.model.RealmId
import i2.keycloak.f2.group.domain.model.GroupId
import org.assertj.core.api.Assertions
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.GroupRepresentation
import javax.ws.rs.NotFoundException


fun AssertionKC.group(keycloak: Keycloak): AssertionGroup = AssertionGroup(keycloak)

class AssertionGroup(
	private val keycloak: Keycloak,
) {
	companion object

	fun exists(realmId: String, id: GroupId) {
		try {
			val group = getGroupRepresentation(realmId, id)
			Assertions.assertThat(group).isNotNull
		} catch (e: NotFoundException) {
			Assertions.fail("Group[${id}] not found", e)
		}
	}

	fun notExists(realmId: String, id: GroupId) {
		try {
			getGroupRepresentation(realmId, id)
			Assertions.fail("Group[${id}] exists")
		} catch (e: NotFoundException) {
			Assertions.assertThat(true).isTrue
		}
	}

	fun assertThat(realmId: RealmId, id: GroupId): GroupAssert {
		exists(realmId, id)
		val group = getGroupRepresentation(realmId, id)
		return GroupAssert(group, realmId)
	}

	private fun getGroupRepresentation(realmId: String, id: String): GroupRepresentation {
		return keycloak.realm(realmId).groups().group(id).toRepresentation()
	}

	inner class GroupAssert(
		private val group: GroupRepresentation,
		private val realmId: RealmId
	) {
		fun hasFields(
            id: GroupId = group.id,
            name: String = group.name,
            realmRoles: List<String> = group.realmRoles,
            attributes: Map<String, String> = group.attributes.mapValues { (_, value) -> value.first() },
		): GroupAssert {
			Assertions.assertThat(group.id).isEqualTo(id)
			Assertions.assertThat(group.name).isEqualTo(name)
			Assertions.assertThat(group.realmRoles).isEqualTo(realmRoles)
			attributes.forEach { (key, value) ->
				Assertions.assertThat(group.attributes[key]).isEqualTo(listOf(value))
			}
			return this
		}

		fun haveParentGroup(parentGroupId: GroupId) {
			try {
				val parentGroup = getGroupRepresentation(realmId, parentGroupId)
				Assertions.assertThat(parentGroup.subGroups.map { it.id }).contains(group.id)
			} catch (e: NotFoundException) {
				Assertions.fail("Parent Group[${parentGroupId}] not found", e)
			}
		}
	}
}
