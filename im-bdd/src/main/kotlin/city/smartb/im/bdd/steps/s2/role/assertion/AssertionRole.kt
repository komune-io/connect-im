//package city.smartb.im.bdd.steps.s2.role.assertion
//
//import city.smartb.im.bdd.assertion.AssertionBdd
//import city.smartb.im.privilege.domain.role.model.Role
//import city.smartb.im.privilege.domain.role.query.RoleGetByIdQuery
//import f2.dsl.fnc.invoke
//import org.assertj.core.api.Assertions
//
//fun AssertionBdd.role(api: RoleQueryApi) = AssertionRole(api)
//
//class AssertionRole(
//    val api: RoleQueryApi,
//) {
//    suspend fun assertThat(id: String): RoleAssert {
//        val role = api.roleGetById().invoke(RoleGetByIdQuery(id = id)).item!!
//        return assertThat(role)
//    }
//    fun assertThat(entity: Role) = RoleAssert(entity)
//
//    inner class RoleAssert(
//        private val role: Role
//    ) {
//        fun hasFields(
//            id: String = role.id,
//            name: String = role.name,
//            description: String? = role.description,
//            isClientRole: Boolean = role.isClientRole
//        ) = also {
//            Assertions.assertThat(role.id).isEqualTo(id)
//            Assertions.assertThat(role.name).isEqualTo(name)
//            Assertions.assertThat(role.description).isEqualTo(description)
//            Assertions.assertThat(role.isClientRole).isEqualTo(isClientRole)
//        }
//    }
//}
