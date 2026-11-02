package io.komune.im.f2.space.lib.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.keycloak.representations.idm.RealmRepresentation

class RealmLocalesTest {

    @Test
    fun `defining locales on a realm without internationalization enables it`() {
        val realm = RealmRepresentation().apply { isInternationalizationEnabled = false }

        realm.applyLocales(listOf("fr", "en"))

        assertThat(realm.isInternationalizationEnabled).isTrue()
        assertThat(realm.defaultLocale).isEqualTo("fr")
        assertThat(realm.supportedLocales).containsExactly("fr", "en")
    }

    @Test
    fun `no locales leaves the realm languages to Keycloak`() {
        val realm = RealmRepresentation().apply { isInternationalizationEnabled = false }

        realm.applyLocales(null)

        assertThat(realm.isInternationalizationEnabled).isFalse()
        assertThat(realm.defaultLocale).isNull()
        assertThat(realm.supportedLocales).isNull()
    }

    @Test
    fun `a space lists its realm default locale first`() {
        val realm = RealmRepresentation().apply {
            realm = "space"
            supportedLocales = linkedSetOf("en", "fr", "de")
            defaultLocale = "fr"
        }

        assertThat(realm.toSpace().locales).containsExactly("fr", "en", "de")
    }

    @Test
    fun `redefining a space with its own locales keeps its default locale`() {
        val realm = RealmRepresentation().apply {
            realm = "space"
            supportedLocales = linkedSetOf("en", "fr")
            defaultLocale = "fr"
        }

        realm.applyLocales(realm.toSpace().locales)

        assertThat(realm.defaultLocale).isEqualTo("fr")
    }
}
