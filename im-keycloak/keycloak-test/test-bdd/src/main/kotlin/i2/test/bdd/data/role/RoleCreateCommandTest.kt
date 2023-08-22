package i2.test.bdd.data.role

import city.smartb.im.infra.keycloak.AuthRealm
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.test.bdd.data.DataTest
import java.util.UUID

fun DataTest.roleCreateCommand(
    auth: AuthRealm,
    realmId: RoleName,
    name: RoleName = UUID.randomUUID().toString(),
    description: String? = null,
    isClientRole: Boolean = false,
    composites: List<RoleName> = emptyList(),
) = RoleCreateCommand(
    name = name,
    description = description,
    isClientRole = isClientRole,
    composites = composites,
    auth = auth,
    realmId = realmId
)
