package i2.test.bdd.data.role

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.roleCreateCommand(
    auth: AuthRealm,
    realmId: RoleIdentifier,
    name: RoleIdentifier = UUID.randomUUID().toString(),
    description: String? = null,
    isClientRole: Boolean = false,
    composites: List<RoleIdentifier> = emptyList(),
) {
    TODO()
//    RoleCreateCommand(
//        name = name,
//        description = description,
//        isClientRole = isClientRole,
//        composites = composites,
//        auth = auth,
//        realmId = realmId
//    )
}
