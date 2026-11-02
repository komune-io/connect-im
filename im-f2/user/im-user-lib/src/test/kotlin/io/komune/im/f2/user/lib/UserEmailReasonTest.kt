package io.komune.im.f2.user.lib

import io.komune.im.core.user.domain.command.UserCoreEmailReason
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserEmailReasonTest {

    @Test
    fun `a creation handing over the password is a welcome`() {
        assertThat(creationEmailReason(listOf("UPDATE_PASSWORD"))).isEqualTo(UserCoreEmailReason.WELCOME)
        assertThat(creationEmailReason(listOf("VERIFY_EMAIL", "UPDATE_PASSWORD")))
            .isEqualTo(UserCoreEmailReason.WELCOME)
    }

    @Test
    fun `a creation asking only to verify the address is not`() {
        assertThat(creationEmailReason(listOf("VERIFY_EMAIL"))).isEqualTo(UserCoreEmailReason.ACCOUNT_ACTION)
    }

    @Test
    fun `a reset stays a welcome until the account holds a credential`() {
        assertThat(resetEmailReason(hasCredentials = false)).isEqualTo(UserCoreEmailReason.WELCOME)
        assertThat(resetEmailReason(hasCredentials = true)).isEqualTo(UserCoreEmailReason.ACCOUNT_ACTION)
    }
}
