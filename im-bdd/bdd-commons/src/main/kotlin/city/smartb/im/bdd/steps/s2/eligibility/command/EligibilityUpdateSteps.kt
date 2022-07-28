//package city.smartb.im.bdd.steps.s2.eligibility.command
//
//import city.smartb.im.bdd.CucumberStepsDefinition
//import city.smartb.im.bdd.assertion.AssertionBdd
//import city.smartb.im.bdd.assertion.eligibility
//import city.smartb.im.bdd.data.TestContextKey
//import city.smartb.im.bdd.data.parser.extractEligibilityCondition
//import city.smartb.im.bdd.data.parser.extractList
//import city.smartb.im.s2.eligibility.api.EligibilityAggregateService
//import city.smartb.im.s2.eligibility.api.entity.EligibilityRepository
//import city.smartb.im.s2.eligibility.domain.command.EligibilityUpdateCommand
//import city.smartb.im.s2.eligibility.domain.model.EligibilityCondition
//import io.cucumber.datatable.DataTable
//import io.cucumber.java8.En
//import org.springframework.beans.factory.annotation.Autowired
//
//class EligibilityUpdateSteps: En, CucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var eligibilityAggregateService: EligibilityAggregateService
//
//    @Autowired
//    private lateinit var eligibilityRepository: EligibilityRepository
//
//    private lateinit var command: EligibilityUpdateCommand
//
//    init {
//        DataTableType(::eligibilityUpdateParams)
//
//        When("I update the eligibility") {
//            step {
//                updateEligibility(eligibilityUpdateParams(null))
//            }
//        }
//
//        When("I update the eligibility:") { params: EligibilityUpdateParams ->
//            step {
//                updateEligibility(params)
//            }
//        }
//
//        Given("The eligibility is updated") {
//            step {
//                updateEligibility(eligibilityUpdateParams(null))
//            }
//        }
//
//        Given("The eligibility is updated:") { params: EligibilityUpdateParams ->
//            step {
//                updateEligibility(params)
//            }
//        }
//
//        Given("The eligibilities are updated:") { dataTable: DataTable ->
//            step {
//                dataTable.asList(EligibilityUpdateParams::class.java)
//                    .forEach { updateEligibility(it) }
//            }
//        }
//
//        Then("The eligibility should be updated") {
//            step {
//                val eligibilityId = context.eligibilityIds.lastUsed
//                AssertionBdd.eligibility(eligibilityRepository).assertThat(eligibilityId).hasFields(
//                    condition = command.condition,
//                    conditionData = command.conditionData,
//                )
//            }
//        }
//    }
//
//    private suspend fun updateEligibility(params: EligibilityUpdateParams) {
//        command = EligibilityUpdateCommand(
//            id = context.eligibilityIds.safeGet(params.identifier),
//            condition = params.condition,
//            conditionData = params.conditionData
//        )
//        eligibilityAggregateService.update(command).id
//    }
//
//    private fun eligibilityUpdateParams(entry: Map<String, String>?) = EligibilityUpdateParams(
//        identifier = entry?.get("identifier") ?: context.eligibilityIds.lastUsedKey,
//        condition = entry?.extractEligibilityCondition("condition") ?: EligibilityCondition.ALWAYS,
//        conditionData = entry?.extractList("conditionData") ?: emptyList()
//    )
//
//    private data class EligibilityUpdateParams(
//        val identifier: TestContextKey,
//        val condition: EligibilityCondition,
//        val conditionData: Collection<String>
//    )
//}
