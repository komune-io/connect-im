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
            roles: List<String> = user.roles.assignedRoles,
            attributes: Map<String, String> = user.attributes,
            sendEmailLink: Boolean? = user.sendEmailLink,
            enabled: Boolean = user.enabled,
            creationDate: Long = user.creationDate
        ) = also {
            Assertions.assertThat(user.email).isEqualTo(email)
            Assertions.assertThat(user.givenName).isEqualTo(givenName)
            Assertions.assertThat(user.familyName).isEqualTo(familyName)
            Assertions.assertThat(user.phone).isEqualTo(phone)
            Assertions.assertThat(user.roles.assignedRoles).isEqualTo(roles)
            Assertions.assertThat(user.attributes).isEqualTo(attributes)
            Assertions.assertThat(user.sendEmailLink).isEqualTo(sendEmailLink)
            Assertions.assertThat(user.enabled).isEqualTo(enabled)
            Assertions.assertThat(user.creationDate).isEqualTo(creationDate)
            Assertions.assertThat(user.memberOf?.id).isEqualTo(memberOf)
        }.hasAddress(address)

        fun hasAddress(address: AddressDTO?) = also {
            Assertions.assertThat(user.address?.city).isEqualTo(address?.city)
            Assertions.assertThat(user.address?.postalCode).isEqualTo(address?.postalCode)
            Assertions.assertThat(user.address?.street).isEqualTo(address?.street)
        }
    }
}
