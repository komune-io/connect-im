package city.smartb.im.bdd.steps.s2.role.assertion

import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.role.api.RoleCommandApi
import city.smartb.im.role.api.RoleQueryApi
import city.smartb.im.role.domain.features.query.RoleGetByIdQuery
import city.smartb.im.role.domain.model.RoleId
import city.smartb.im.role.domain.model.RoleModel
import f2.dsl.fnc.invoke
import i2.keycloak.master.domain.RealmId
import org.assertj.core.api.Assertions

fun AssertionBdd.role(api: RoleQueryApi, realmId: RealmId) = AssertionRole(api, realmId)

class AssertionRole(
    val api: RoleQueryApi,
    val realmId: RealmId
)
    {
    suspend fun assertThat(id: RoleId): RoleAssert {
        val role = api.roleGetById().invoke(RoleGetByIdQuery(id, realmId)).item!!
        return assertThat(role)
    }
    fun assertThat(entity: RoleModel) = RoleAssert(entity)

    inner class RoleAssert(
        private val role: RoleModel
    ) {
        fun hasFields(
            id: RoleId = role.id,
            name: String = role.name,
            description: String? = role.description,
            isClientRole: Boolean = role.isClientRole
        ) = also {
            Assertions.assertThat(role.id).isEqualTo(id)
            Assertions.assertThat(role.name).isEqualTo(description)
            Assertions.assertThat(role.description).isEqualTo(description)
            Assertions.assertThat(role.isClientRole).isEqualTo(isClientRole)
        }
    }
}
