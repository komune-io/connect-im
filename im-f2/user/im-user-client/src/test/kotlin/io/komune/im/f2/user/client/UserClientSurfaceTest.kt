package io.komune.im.f2.user.client

import kotlin.reflect.KClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Each [UserClient] method delegates with `this::method.name`, so the **method name is the
 * remote function name**. Renaming one silently repoints the call at a route the server
 * does not serve, with no compile error on either side.
 */
class UserClientSurfaceTest {

    private val operations: List<String> = UserClient::class.members.map { it.name }

    @Test
    fun exposesEveryDocumentedOperation() {
        EXPECTED.forEach { assertThat(operations).contains(it) }
    }

    @Test
    fun theClientClassIsOpenSoConsumersCanDecorateIt() {
        assertThat((UserClient::class as KClass<*>).isFinal).isFalse()
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
            "userUpdateEmail",
            "userUpdatePassword",
            "userDisableMfa",
            "userDisable",
            "userEnable",
            "userDelete"
        )
    }
}
