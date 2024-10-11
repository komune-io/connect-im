package io.komune.im.bdd.core.user.data

import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId
import io.komune.im.commons.utils.parseJson
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.user.domain.model.User
import io.komune.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.UserRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

fun AssertionBdd.user(client: KeycloakClient) = AssertionUser(client)

class AssertionUser(
    private val client: KeycloakClient
): AssertionApiEntity<UserRepresentation, UserId, AssertionUser.UserAssert>() {
    override suspend fun findById(id: UserId): UserRepresentation? = try {
        client.user(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        null
    }

    override suspend fun assertThat(entity: UserRepresentation) = UserAssert(entity)

    suspend fun notExistsByEmail(email: String) {
        Assertions.assertThat(getByEmail(email)).isNull()
    }

    private fun getByEmail(email: String): UserRepresentation? {
        return client.users().search("", "", "", email, 0, 1).firstOrNull()
    }

    inner class UserAssert(
        private val user: UserRepresentation
    ) {
        private val singleAttributes = user.attributes.orEmpty()
            .mapValues { (_, values) -> values.firstOrNull() }
            .filterValues { !it.isNullOrBlank() } as Map<String, String>

        private val userMemberOf: OrganizationId? = singleAttributes[User::memberOf.name]
        private val userAddress: Address? = singleAttributes[User::address.name]?.parseJson()
        private val userPhone: String? = singleAttributes[User::phone.name]
        private val userDisabledBy: UserId? = singleAttributes[User::disabledBy.name]
        private val userDisabledDate: Long? = singleAttributes[User::disabledDate.name]?.toLong()
        private val userIsApiKey: Boolean = singleAttributes[UserModel::isApiKey.name].toBoolean()
        private val userRoles = client.user(user.id)
            .roles()
            .realmLevel()
            .listAll()
            .map { it.name }
            .minus(client.defaultRealmRole)

        fun hasFields(
            memberOf: OrganizationId? = userMemberOf,
            email: String = user.email.orEmpty(),
            givenName: String = user.firstName.orEmpty(),
            familyName: String = user.lastName.orEmpty(),
            address: Address? = userAddress,
            phone: String? = userPhone,
            roles: List<RoleIdentifier> = userRoles,
            attributes: Map<String, String> = singleAttributes,
            enabled: Boolean = user.isEnabled,
            disabledBy: UserId? = userDisabledBy,
            creationDate: Long = user.createdTimestamp,
            disabledDate: Long? = userDisabledDate,
            isApiKey: Boolean = userIsApiKey
        ) = also {
            Assertions.assertThat(userMemberOf).isEqualTo(memberOf)
            Assertions.assertThat(user.email.orEmpty()).isEqualTo(email)
            Assertions.assertThat(user.firstName.orEmpty()).isEqualTo(givenName)
            Assertions.assertThat(user.lastName.orEmpty()).isEqualTo(familyName)
            Assertions.assertThat(userAddress).isEqualTo(address)
            Assertions.assertThat(userPhone).isEqualTo(phone)
            Assertions.assertThat(userRoles).containsExactlyInAnyOrderElementsOf(roles)
            Assertions.assertThat(singleAttributes).containsAllEntriesOf(attributes)
            Assertions.assertThat(user.isEnabled).isEqualTo(enabled)
            Assertions.assertThat(user.createdTimestamp).isEqualTo(creationDate)
            Assertions.assertThat(userDisabledBy).isEqualTo(disabledBy)
            Assertions.assertThat(userDisabledDate).isEqualTo(disabledDate)
            Assertions.assertThat(userIsApiKey).isEqualTo(isApiKey)
        }

        fun isAnonymized() = also {
            Assertions.assertThat(user.email).endsWith("@anonymous.com")
            Assertions.assertThat(user.firstName).isEqualTo("anonymous")
            Assertions.assertThat(user.lastName).isEqualTo("anonymous")
            Assertions.assertThat(userPhone).isEqualTo("")
        }

        fun matches(other: User) = hasFields(
            memberOf = other.memberOf?.id,
            email = other.email,
            givenName = other.givenName,
            familyName = other.familyName,
            address = other.address,
            phone = other.phone,
            roles = other.roles.map(Role::identifier),
            attributes = other.attributes,
            enabled = other.enabled,
            creationDate = other.creationDate,
            disabledDate = other.disabledDate,
            disabledBy = other.disabledBy
        )
    }
}
