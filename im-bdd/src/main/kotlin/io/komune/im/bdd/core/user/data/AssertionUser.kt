package io.komune.im.bdd.core.user.data

import io.komune.im.bdd.core.organization.data.AssertionOrganizationRef
import io.komune.im.commons.model.Address
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.RoleIdentifier
import io.komune.im.commons.model.UserId
import io.komune.im.commons.utils.parseJson
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.f2.privilege.domain.role.model.Role
import io.komune.im.f2.user.domain.model.User
import io.komune.im.infra.keycloak.client.KeycloakClient
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException
import org.assertj.core.api.Assertions
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.user(client: KeycloakClient) = AssertionUser(client)

class AssertionUser(
    private val client: KeycloakClient
): AssertionApiEntity<UserRepresentation, UserId, AssertionUser.UserAssert>() {

    private val logger = LoggerFactory.getLogger(AssertionOrganizationRef::class.java)

    override suspend fun findById(id: UserId): UserRepresentation? = try {
        client.user(id).toRepresentation()
    } catch (e: JakartaNotFoundException) {
        logger.debug("User not found with id: $id", e)
        null
    }

    override suspend fun assertThat(entity: UserRepresentation) = UserAssert(entity)

    fun notExistsByEmail(email: String) {
        Assertions.assertThat(getByEmail(email)).isNull()
    }

    private fun getByEmail(email: String): UserRepresentation? {
        return client.users().search("", "", "", email, 0, 1).firstOrNull()
    }

    inner class UserAssert(
        private val user: UserRepresentation
    ) {
        private val singleAttributes = user.attributes.orEmpty()
            .mapNotNull { (key, values) -> values.firstOrNull()?.takeIf { it.isNotBlank() }?.let { key to it } }
            .toMap()

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

        /** What the asserted entity holds, as fields to compare. */
        fun actual() = UserFields(
            memberOf = userMemberOf,
            email = user.email.orEmpty(),
            givenName = user.firstName.orEmpty(),
            familyName = user.lastName.orEmpty(),
            address = userAddress,
            phone = userPhone,
            roles = userRoles,
            attributes = singleAttributes,
            enabled = user.isEnabled,
            disabledBy = userDisabledBy,
            creationDate = user.createdTimestamp,
            disabledDate = userDisabledDate,
            isApiKey = userIsApiKey,
        )

        /** Asserts the entity holds [expected], its own fields by default. */
        fun hasFields(expected: UserFields = actual()) = also {
            Assertions.assertThat(userMemberOf).isEqualTo(expected.memberOf)
            Assertions.assertThat(user.email.orEmpty()).isEqualTo(expected.email)
            Assertions.assertThat(user.firstName.orEmpty()).isEqualTo(expected.givenName)
            Assertions.assertThat(user.lastName.orEmpty()).isEqualTo(expected.familyName)
            Assertions.assertThat(userAddress).isEqualTo(expected.address)
            Assertions.assertThat(userPhone).isEqualTo(expected.phone)
            Assertions.assertThat(userRoles).containsExactlyInAnyOrderElementsOf(expected.roles)
            Assertions.assertThat(singleAttributes).containsAllEntriesOf(expected.attributes)
            Assertions.assertThat(user.isEnabled).isEqualTo(expected.enabled)
            Assertions.assertThat(user.createdTimestamp).isEqualTo(expected.creationDate)
            Assertions.assertThat(userDisabledBy).isEqualTo(expected.disabledBy)
            Assertions.assertThat(userDisabledDate).isEqualTo(expected.disabledDate)
            Assertions.assertThat(userIsApiKey).isEqualTo(expected.isApiKey)
        }

        /** Compares the entity with its own fields as [change] rewrites them. */
        inline fun hasFields(change: (UserFields) -> UserFields) = hasFields(change(actual()))

        fun isAnonymized() = also {
            Assertions.assertThat(user.email).endsWith("@anonymous.com")
            Assertions.assertThat(user.firstName).isEqualTo("anonymous")
            Assertions.assertThat(user.lastName).isEqualTo("anonymous")
            Assertions.assertThat(userPhone).isEqualTo("")
        }

        /** Asserts the entity holds the fields of the given model. */
        fun matches(other: User) = hasFields { fields -> fields.copy(
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
        ) }
    }
}

/** The fields a user assertion compares. */
data class UserFields(
    val memberOf: OrganizationId?,
    val email: String,
    val givenName: String,
    val familyName: String,
    val address: Address?,
    val phone: String?,
    val roles: List<RoleIdentifier>,
    val attributes: Map<String, String>,
    val enabled: Boolean,
    val disabledBy: UserId?,
    val creationDate: Long,
    val disabledDate: Long?,
    val isApiKey: Boolean,
)
