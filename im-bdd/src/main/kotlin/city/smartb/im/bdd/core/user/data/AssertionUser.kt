package city.smartb.im.bdd.core.user.data

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.RoleIdentifier
import city.smartb.im.commons.model.UserId
import city.smartb.im.commons.utils.parseJson
import city.smartb.im.f2.privilege.domain.role.model.RoleDTOBase
import city.smartb.im.f2.user.domain.model.UserDTOBase
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.UserRepresentation
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.user(client: KeycloakClient) = AssertionUser(client)

class AssertionUser(
    private val client: KeycloakClient
): AssertionApiEntity<UserRepresentation, UserId, AssertionUser.UserAssert>() {
    override suspend fun findById(id: UserId): UserRepresentation? = try {
        client.user(id).toRepresentation()
    } catch (e: javax.ws.rs.NotFoundException) {
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
        private val singleAttributes = user.attributes
            .mapValues { (_, values) -> values.firstOrNull() }
            .filterValues { !it.isNullOrBlank() } as Map<String, String>

        private val userMemberOf: OrganizationId? = singleAttributes[UserDTOBase::memberOf.name]
        private val userAddress: Address? = singleAttributes[UserDTOBase::address.name]?.parseJson()
        private val userPhone: String? = singleAttributes[UserDTOBase::phone.name]
        private val userDisabledBy: UserId? = singleAttributes[UserDTOBase::disabledBy.name]
        private val userDisabledDate: Long? = singleAttributes[UserDTOBase::disabledDate.name]?.toLong()
        private val userRoles = client.user(user.id)
            .roles()
            .realmLevel()
            .listAll()
            .map { it.name }
            .minus(client.defaultRealmRole)

        fun hasFields(
            memberOf: OrganizationId? = userMemberOf,
            email: String = user.email,
            givenName: String = user.firstName,
            familyName: String = user.lastName,
            address: Address? = userAddress,
            phone: String? = userPhone,
            roles: List<RoleIdentifier> = userRoles,
            attributes: Map<String, String> = singleAttributes,
            enabled: Boolean = user.isEnabled,
            disabledBy: UserId? = userDisabledBy,
            creationDate: Long = user.createdTimestamp,
            disabledDate: Long? = userDisabledDate
        ) = also {
            Assertions.assertThat(userMemberOf).isEqualTo(memberOf)
            Assertions.assertThat(user.email).isEqualTo(email)
            Assertions.assertThat(user.firstName).isEqualTo(givenName)
            Assertions.assertThat(user.lastName).isEqualTo(familyName)
            Assertions.assertThat(userAddress).isEqualTo(address)
            Assertions.assertThat(userPhone).isEqualTo(phone)
            Assertions.assertThat(userRoles).containsExactlyInAnyOrderElementsOf(roles)
            Assertions.assertThat(singleAttributes).containsAllEntriesOf(attributes)
            Assertions.assertThat(user.isEnabled).isEqualTo(enabled)
            Assertions.assertThat(user.createdTimestamp).isEqualTo(creationDate)
            Assertions.assertThat(userDisabledBy).isEqualTo(disabledBy)
            Assertions.assertThat(userDisabledDate).isEqualTo(disabledDate)
        }

        fun isAnonymized() = also {
            Assertions.assertThat(user.email).endsWith("@anonymous.com")
            Assertions.assertThat(user.firstName).isEqualTo("anonymous")
            Assertions.assertThat(user.lastName).isEqualTo("anonymous")
            Assertions.assertThat(userPhone).isEqualTo("")
        }

        fun matches(other: UserDTOBase) = hasFields(
            memberOf = other.memberOf?.id,
            email = other.email,
            givenName = other.givenName,
            familyName = other.familyName,
            address = other.address,
            phone = other.phone,
            roles = other.roles.map(RoleDTOBase::identifier),
            attributes = other.attributes,
            enabled = other.enabled,
            creationDate = other.creationDate,
            disabledDate = other.disabledDate,
            disabledBy = other.disabledBy
        )
    }
}
