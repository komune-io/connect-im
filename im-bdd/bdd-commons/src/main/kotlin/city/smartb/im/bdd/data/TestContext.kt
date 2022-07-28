package city.smartb.im.bdd.data

import f2.dsl.cqrs.Event
import org.springframework.stereotype.Component

typealias TestContextKey = String

@Component
class TestContext {
    private val entityLists = mutableListOf<TestEntities<*, *>>()

//    val eligibilityIds = testEntities<String, ProjectId>("Eligibility")
//    val projectIds = testEntities<String, ProjectId>("Project")

    val errors = ExceptionList()
    val events = mutableListOf<Event>()

    final var fetched = FetchContext()
        private set

    private fun <K: Any, V> testEntities(name: String) = TestEntities<K, V>(name)
        .also(entityLists::add)

    fun reset() {
        entityLists.forEach(TestEntities<*, *>::reset)
        errors.reset()
        events.clear()
        fetched = FetchContext()
    }
}
