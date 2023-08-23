package i2.test.bdd.data.role

import city.smartb.im.infra.keycloak.AuthRealm
import city.smartb.im.infra.keycloak.RealmId
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleAddCompositesCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.roleAddCompositesCommand(
    auth: AuthRealm,
    realmId: RealmId,
    roleName: RoleName = UUID.randomUUID().toString(),
    composites: List<RoleName> = emptyList()
) = RoleAddCompositesCommand(
    roleName = roleName,
    composites = composites,
    auth = auth,
    realmId = realmId
)
