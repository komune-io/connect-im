package io.komune.im.f2.space.lib.model

import io.komune.im.f2.space.domain.model.Space
import org.keycloak.representations.idm.RealmRepresentation

fun RealmRepresentation.toSpace() = Space(
    identifier = realm,
    displayName = displayName,
    theme = loginTheme,
    smtp = smtpServer,
    // The first locale of a space is its realm's default; Keycloak returns them unordered.
    locales = supportedLocales?.sortedByDescending { it == defaultLocale }
)
