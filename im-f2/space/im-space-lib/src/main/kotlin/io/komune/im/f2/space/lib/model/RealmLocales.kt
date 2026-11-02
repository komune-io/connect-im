package io.komune.im.f2.space.lib.model

import org.keycloak.representations.idm.RealmRepresentation

internal fun RealmRepresentation.applyLocales(locales: List<String>?) = apply {
    supportedLocales = locales?.toSet()
    defaultLocale = locales?.firstOrNull()
    if (!locales.isNullOrEmpty()) {
        isInternationalizationEnabled = true
    }
}
