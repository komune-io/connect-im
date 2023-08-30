package city.smartb.im.space.lib.model

import city.smartb.im.space.domain.model.Space
import org.keycloak.representations.idm.RealmRepresentation

fun RealmRepresentation.toSpace() = Space(
    identifier = id
)
