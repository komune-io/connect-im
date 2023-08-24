package city.smartb.im.bdd.steps.s2.user.assertion

import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.commons.model.AddressDTO
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.query.UserGetByEmailQuery
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.model.User
import city.smartb.im.user.domain.model.UserId
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import org.assertj.core.api.Assertions

fun AssertionBdd.user(api: UserEndpoint) = AssertionUser(api)

class AssertionUser(
    val api: UserEndpoint
) {

    suspend fun assertThat(id: UserId): UserAssert {
        val user = api.userGet().invoke(UserGetQuery(id = id)).item!!
        return assertThat(user)
    }

    fun assertThat(entity: User) = UserAssert(entity)

    suspend fun notExists(id: UserId) {
        Assertions.assertThat(get(id)).isNull()
    }

    suspend fun notExistsByEmail(email: String) {
        Assertions.assertThat(getByEmail(email)).isNull()
    }

    suspend fun get(id: UserId): User? {
        return UserGetQuery(id).invokeWith(api.userGet()).item
    }

    private suspend fun getByEmail(email: String): User? {
        return UserGetByEmailQuery(email).invokeWith(api.userGetByEmail()).item
    }

    inner class UserAssert(
        private val user: User
    ) {
        fun hasFields(
            memberOf: OrganizationId? = user.memberOf?.id,
            email: String = user.email,
            givenName: String = user.givenName,
            familyName: String = user.familyName,
            address: AddressDTO? = user.address,
            phone: String? = user.phone,
            roles: List<String> = user.roles,
            attributes: Map<String, String> = user.attributes,
            enabled: Boolean = user.enabled,
            disabledBy: UserId? = user.disabledBy,
            creationDate: Long = user.creationDate,
            disabledDate: Long? = user.disabledDate
        ) = also {
            Assertions.assertThat(user.email).isEqualTo(email)
            Assertions.assertThat(user.givenName).isEqualTo(givenName)
            Assertions.assertThat(user.familyName).isEqualTo(familyName)
            Assertions.assertThat(user.phone).isEqualTo(phone)
            Assertions.assertThat(user.roles).isEqualTo(roles)
            Assertions.assertThat(user.attributes).containsAnyOf(*attributes.entries.toTypedArray())
            Assertions.assertThat(user.enabled).isEqualTo(enabled)
            Assertions.assertThat(user.creationDate).isEqualTo(creationDate)
            Assertions.assertThat(user.memberOf?.id).isEqualTo(memberOf)
            Assertions.assertThat(user.disabledBy).isEqualTo(disabledBy)
            Assertions.assertThat(user.disabledDate).isEqualTo(disabledDate)
        }.hasAddress(address)

        fun hasAddress(address: AddressDTO?) = also {
            Assertions.assertThat(user.address?.city).isEqualTo(address?.city)
            Assertions.assertThat(user.address?.postalCode).isEqualTo(address?.postalCode)
            Assertions.assertThat(user.address?.street).isEqualTo(address?.street)
        }

        fun isAnonymized() = also {
            Assertions.assertThat(user.email).endsWith("@anonymous.com")
            Assertions.assertThat(user.givenName).isEqualTo("anonymous")
            Assertions.assertThat(user.familyName).isEqualTo("anonymous")
            Assertions.assertThat(user.phone).isEqualTo("")
            Assertions.assertThat(user.roles).isEqualTo(user.roles)
        }

        fun matches(other: User) = hasFields(
            memberOf = other.memberOf?.id,
            email = other.email,
            givenName = other.givenName,
            familyName = other.familyName,
            address = other.address,
            phone = other.phone,
            roles = other.roles,
            attributes = other.attributes,
            enabled = other.enabled,
            creationDate = other.creationDate
        )
    }
}
