package i2.keycloak.f2.role.domain

import i2.keycloak.master.domain.RealmId

fun defaultRealmRole(realmId: RealmId) = "default-roles-$realmId"
