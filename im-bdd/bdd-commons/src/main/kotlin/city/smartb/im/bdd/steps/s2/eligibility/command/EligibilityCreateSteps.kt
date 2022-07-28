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
//import city.smartb.im.s2.eligibility.domain.automate.EligibilityState
//import city.smartb.im.s2.eligibility.domain.command.EligibilityCreateCommand
//import city.smartb.im.s2.eligibility.domain.model.EligibilityCondition
//import io.cucumber.datatable.DataTable
//import io.cucumber.java8.En
//import org.springframework.beans.factory.annotation.Autowired
//
//class EligibilityCreateSteps: En, CucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var eligibilityAggregateService: EligibilityAggregateService
//
//    @Autowired
//    private lateinit var eligibilityRepository: EligibilityRepository
//
//    private lateinit var command: EligibilityCreateCommand
//
//    init {
//        DataTableType(::eligibilityInitParams)
//
//        When("I create an eligibility") {
//            step {
//                createEligibility(eligibilityInitParams(null))
//            }
//        }
//
//        When("I create an eligibility:") { params: EligibilityInitParams ->
//            step {
//                createEligibility(params)
//            }
//        }
//
//        Given("An eligibility is created") {
//            step {
//                createEligibility(eligibilityInitParams(null))
//            }
//        }
//
//        Given("An eligibility is created:") { params: EligibilityInitParams ->
//            step {
//                createEligibility(params)
//            }
//        }
//
//        Given("Some eligibilities are created:") { dataTable: DataTable ->
//            step {
//                dataTable.asList(EligibilityInitParams::class.java)
//                    .forEach { createEligibility(it) }
//            }
//        }
//
//        Then("The eligibility should be created") {
//            step {
//                val eligibilityId = context.eligibilityIds.lastUsed
//                AssertionBdd.eligibility(eligibilityRepository).assertThat(eligibilityId).hasFields(
//                    status = EligibilityState.Exists,
//                    legalCategory3Code = command.legalCategory3Code,
//                    condition = command.condition,
//                    conditionData = command.conditionData,
//                )
//            }
//        }
//    }
//
//    private suspend fun createEligibility(params: EligibilityInitParams) = context.eligibilityIds.register(params.identifier) {
//        command = EligibilityCreateCommand(
//            legalCategory3Code = params.legalCategory3Code,
//            condition = params.condition,
//            conditionData = params.conditionData
//        )
//        eligibilityAggregateService.create(command).id
//    }
//
//    private fun eligibilityInitParams(entry: Map<String, String>?) = EligibilityInitParams(
//        identifier = entry?.get("identifier").orRandom(),
//        legalCategory3Code = entry?.get("legalCategory3Code") ?: "6666",
//        condition = entry?.extractEligibilityCondition("condition") ?: EligibilityCondition.ALWAYS,
//        conditionData = entry?.extractList("conditionData") ?: emptyList()
//    )
//
//    private data class EligibilityInitParams(
//        val identifier: TestContextKey,
//        val legalCategory3Code: String,
//        val condition: EligibilityCondition,
//        val conditionData: Collection<String>
//    )
//}
