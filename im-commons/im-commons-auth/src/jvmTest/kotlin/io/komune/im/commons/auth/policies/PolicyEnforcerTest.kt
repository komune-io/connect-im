package io.komune.im.commons.auth.policies

import f2.dsl.fnc.F2Function
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * These wrappers sit between the HTTP layer and every command handler, so a mistake here
 * is an authorization bypass rather than a failing feature.
 */
class PolicyEnforcerTest {

    private val echo = F2Function<String, String> { msgs -> msgs }

    @Test
    fun `f2Function applies the handler to each message`() = runTest {
        val fnc = f2Function<String, String> { it.uppercase() }

        val result = fnc.invoke(flowOf("a", "b")).toList()

        assertThat(result).containsExactly("A", "B")
    }

    @Test
    fun `enforce can rewrite the incoming value before the handler sees it`() = runTest {
        val guarded = enforce(echo) { "rewritten-$it" }

        val result = guarded.invoke(flowOf("a")).toList()

        assertThat(result).containsExactly("rewritten-a")
    }

    @Test
    fun `enforce runs before the wrapped function, so a rejected value never reaches it`() = runTest {
        val handled = mutableListOf<String>()
        val recording = F2Function<String, String> { msgs -> msgs.map { handled.add(it); it } }
        val guarded = enforce(recording) { error("denied") }

        runCatching { guarded.invoke(flowOf("a")).toList() }

        assertThat(handled).isEmpty()
    }

    @Test
    fun `verify passes the value through untouched when it allows`() = runTest {
        val guarded = verify(echo) { /* allowed */ }

        val result = guarded.invoke(flowOf("a")).toList()

        assertThat(result).containsExactly("a")
    }

    @Test
    fun `verify rejecting stops the value reaching the handler`() = runTest {
        val handled = mutableListOf<String>()
        val recording = F2Function<String, String> { msgs -> msgs.map { handled.add(it); it } }
        val guarded = verify(recording) { error("denied") }

        runCatching { guarded.invoke(flowOf("a")).toList() }

        assertThat(handled).isEmpty()
    }

    @Test
    fun `verifyAfter inspects the result and still returns it unchanged`() = runTest {
        val seen = mutableListOf<String>()
        val guarded = verifyAfter(echo) { seen.add(it) }

        val result = guarded.invoke(flowOf("a")).toList()

        assertThat(result).containsExactly("a")
        assertThat(seen).containsExactly("a")
    }

    @Test
    fun `verifyAfter rejecting the result surfaces as a failure`() = runTest {
        val guarded = verifyAfter(echo) { error("not yours") }

        val outcome = runCatching { guarded.invoke(flowOf("a")).toList() }

        assertThat(outcome.isFailure).isTrue()
    }
}
