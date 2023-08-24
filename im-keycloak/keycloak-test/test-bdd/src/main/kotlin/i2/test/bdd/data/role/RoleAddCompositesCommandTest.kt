package i2.test.bdd.data.role

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.privilege.domain.role.model.RoleIdentifier
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.roleAddCompositesCommand(
    auth: AuthRealm,
    realmId: RealmId,
    roleName: RoleIdentifier = UUID.randomUUID().toString(),
    composites: List<RoleIdentifier> = emptyList()
) {
    TODO()
//    RoleAddCompositesCommand(
//        roleName = roleName,
//        composites = composites,
//        auth = auth,
//        realmId = realmId
//    )
}
