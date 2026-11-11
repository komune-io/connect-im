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
        assertEquals(expected = "https://app.example.com/*", actual = "https://app.example.com".addWildcard())
    }

    @Test
    fun aTrailingSlashGetsOnlyTheStar() {
        assertEquals(expected = "https://app.example.com/*", actual = "https://app.example.com/".addWildcard())
    }

    @Test
    fun anAlreadyWildcardedUrlIsLeftAlone() {
        assertEquals(expected = "https://app.example.com/*", actual = "https://app.example.com/*".addWildcard())
    }

    @Test
    fun isIdempotent() {
        val once = "https://app.example.com".addWildcard()

        assertEquals(expected = once, actual = once.addWildcard())
    }

    @Test
    fun aPathIsPreserved() {
        assertEquals(
            expected = "https://app.example.com/callback/*",
            actual = "https://app.example.com/callback".addWildcard()
        )
    }

    @Test
    fun aStarThatIsNotSlashStarStillGetsSuffixed() {
        // "…/foo*" is not the "/*" suffix, so it is treated as an ordinary path
        assertEquals(expected = "https://app.example.com/foo*/*", actual = "https://app.example.com/foo*".addWildcard())
    }
}
