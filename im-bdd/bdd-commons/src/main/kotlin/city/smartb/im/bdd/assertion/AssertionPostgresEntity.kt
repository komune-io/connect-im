//package city.smartb.im.bdd.assertion
//
//import city.smartb.im.infra.postgresql.EntityBase
//import org.assertj.core.api.Assertions
//import org.springframework.data.repository.kotlin.CoroutineCrudRepository
//
//abstract class AssertionPostgresEntity<Entity: EntityBase<ID, *>, ID: Any, Asserter> {
//    protected abstract val repository: CoroutineCrudRepository<Entity, ID>
//
//    suspend fun exists(id: ID) {
//        Assertions.assertThat(repository.existsById(id)).isTrue
//    }
//
//    suspend fun notExists(id: ID) {
//        Assertions.assertThat(repository.existsById(id)).isFalse
//    }
//
//    suspend fun assertThat(id: ID): Asserter {
//        exists(id)
//        val entity = repository.findById(id)!!
//        return assertThat(entity)
//    }
//
//    abstract fun assertThat(entity: Entity): Asserter
//}
