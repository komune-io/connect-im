package city.smartb.im.bdd

import city.smartb.im.bdd.data.TestContext
import f2.dsl.cqrs.Event
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class TestApplicationEventListener(
    private val testContext: TestContext
) {

    @EventListener
    fun onApplicationEvent(event: Event) {
        testContext.events.add(event)
    }
}
