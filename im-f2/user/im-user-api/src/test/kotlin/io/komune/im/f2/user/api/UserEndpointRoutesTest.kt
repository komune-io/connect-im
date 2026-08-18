package io.komune.im.f2.user.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Spring Cloud Function routes each `@Bean` by its **method name**, and the matching
 * client derives the same name from its own method. Renaming either side alone breaks
 * the route at runtime with nothing failing at compile time — this pins the server half.
 */
class UserEndpointRoutesTest {

    private val routes = UserEndpoint::class.java.declaredMethods.map { it.name }

    @Test
    fun `exposes every documented route`() {
        EXPECTED.forEach { assertThat(routes).contains(it) }
    }

    @Test
    fun `route names are unique`() {
        val exposed = routes.filter { it in EXPECTED }

        assertThat(exposed).doesNotHaveDuplicates()
    }

    companion object {
        private val EXPECTED = listOf(
            "userGet",
            "userGetByEmail",
            "userExistsByEmail",
            "userPage",
            "userCreate",
            "userUpdate",
            "userResetPassword",
            "userDisableMfa",
            "userUpdateEmail",
            "userUpdatePassword",
            "userDisable",
            "userEnable",
            "userDelete"
        )
    }
}
