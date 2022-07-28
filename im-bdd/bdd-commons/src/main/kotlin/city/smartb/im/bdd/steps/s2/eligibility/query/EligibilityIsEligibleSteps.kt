//package city.smartb.im.bdd.steps.s2.eligibility.query
//
//import city.smartb.im.bdd.CucumberStepsDefinition
//import city.smartb.im.bdd.data.parser.safeExtract
//import city.smartb.im.s2.eligibility.api.EligibilityFinderService
//import io.cucumber.java8.En
//import org.assertj.core.api.Assertions
//import org.springframework.beans.factory.annotation.Autowired
//
//class EligibilityIsEligibleSteps: En, CucumberStepsDefinition() {
//
//    @Autowired
//    private lateinit var eligibilityFinderService: EligibilityFinderService
//
//    private var isEligible: Boolean? = null
//
//    init {
//        DataTableType(::eligibilityCheckParams)
//
//        When("I check my eligibility:") { params: EligibilityCheckParams ->
//            step {
//                checkEligibility(params)
//            }
//        }
//
//        Then("I should be eligible") {
//            Assertions.assertThat(isEligible).isTrue
//        }
//
//        Then("I should not be eligible") {
//            Assertions.assertThat(isEligible).isFalse
//        }
//    }
//
//    private suspend fun checkEligibility(params: EligibilityCheckParams) {
//        isEligible = eligibilityFinderService.isEligible(
//            legalCategory3Code = params.legalCategory3Code,
//            nafCode = params.naf,
//            address = params.address
//        )
//    }
//
//    private fun eligibilityCheckParams(entry: Map<String, String>) = EligibilityCheckParams(
//        legalCategory3Code = entry.safeExtract("legalCategory3Code"),
//        naf = entry["naf"],
//        address = entry["address"]
//    )
//
//    private data class EligibilityCheckParams(
//        val legalCategory3Code: String,
//        val naf: String?,
//        val address: String?
//    )
//}
