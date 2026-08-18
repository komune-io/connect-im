package io.komune.im.commons

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Redirect URIs are handed to Keycloak as client redirect patterns, so the exact
 * wildcard shape decides which callbacks are accepted.
 */
class RedirectUrlExtentionTest {

    @Test
    fun aBareUrlGetsASlashWildcard() {
        assertEquals("https://app.example.com/*", "https://app.example.com".addWildcard())
    }

    @Test
    fun aTrailingSlashGetsOnlyTheStar() {
        assertEquals("https://app.example.com/*", "https://app.example.com/".addWildcard())
    }

    @Test
    fun anAlreadyWildcardedUrlIsLeftAlone() {
        assertEquals("https://app.example.com/*", "https://app.example.com/*".addWildcard())
    }

    @Test
    fun isIdempotent() {
        val once = "https://app.example.com".addWildcard()

        assertEquals(once, once.addWildcard())
    }

    @Test
    fun aPathIsPreserved() {
        assertEquals("https://app.example.com/callback/*", "https://app.example.com/callback".addWildcard())
    }

    @Test
    fun aStarThatIsNotSlashStarStillGetsSuffixed() {
        // "…/foo*" is not the "/*" suffix, so it is treated as an ordinary path
        assertEquals("https://app.example.com/foo*/*", "https://app.example.com/foo*".addWildcard())
    }
}
