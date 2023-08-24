package city.smartb.im.bdd.assertion

import city.smartb.im.bdd.data.TestContext
import org.assertj.core.api.Assertions
import kotlin.reflect.KClass

fun AssertionBdd.events(testContext: TestContext) = AssertionApplicationEvents(testContext)

class AssertionApplicationEvents(
    private val context: TestContext
) {

    fun <Event: Any> assertThat(eventClass: KClass<Event>) = ApplicationEventAssert(eventClass)

    inner class ApplicationEventAssert<Event: Any>(
        eventClass: KClass<Event>
    ) {
        private val events = context.events.filterIsInstance(eventClass.java)

        fun hasNotBeenSent(matcher: (Event) -> Boolean = { true }) {
            hasBeenSent(0, matcher)
        }

        fun hasBeenSent(times: Int, matcher: (Event) -> Boolean = { true }) {
            matching(matcher).hasSize(times)
        }

        fun hasBeenSentAtLeast(times: Int, matcher: (Event) -> Boolean = { true }) {
            matching(matcher).hasSizeGreaterThanOrEqualTo(times)
        }

        fun hasBeenSentAtMost(times: Int, matcher: (Event) -> Boolean = { true }) {
            matching(matcher).hasSizeLessThanOrEqualTo(times)
        }

        fun matching(matcher: (Event) -> Boolean) = Assertions.assertThat(events.filter(matcher))
    }
}
