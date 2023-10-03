package city.smartb.im.f2.space.lib.model

import city.smartb.im.f2.space.domain.model.Space
import org.keycloak.representations.idm.RealmRepresentation

fun RealmRepresentation.toSpace() = Space(
    identifier = realm,
    theme = loginTheme,
    smtp = smtpServer,
    locales = supportedLocales?.toList()
)
