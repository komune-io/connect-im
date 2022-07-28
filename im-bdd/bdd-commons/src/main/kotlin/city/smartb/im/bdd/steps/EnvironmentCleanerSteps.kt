package city.smartb.im.bdd.steps

import city.smartb.im.bdd.data.TestContext
import io.cucumber.java8.En

class EnvironmentCleanerSteps(
    private val context: TestContext,
//    private val lcClient: LcReactiveDataRelationalClient
): En {
    init {
        Before { _ ->
            context.reset()
//            resetPostgresSchema()
        }
    }

//    private fun resetPostgresSchema() = runBlocking {
//        val schema = SchemaBuilderFromEntities.build(lcClient.entities)
//        val statements = lcClient.schemaDialect.createSchemaContent(schema)
//        statements.print(System.out)
//        lcClient.dropCreateSchemaContent(schema).awaitFirstOrNull()
//    }
}
