//package city.smartb.im.bdd.assertion
//
//import city.smartb.im.s2.eligibility.api.entity.EligibilityEntity
//import city.smartb.im.s2.eligibility.api.entity.EligibilityRepository
//import city.smartb.im.s2.eligibility.domain.automate.EligibilityId
//import city.smartb.im.s2.eligibility.domain.automate.EligibilityState
//import city.smartb.im.s2.eligibility.domain.model.EligibilityCondition
//import org.assertj.core.api.Assertions
//
//fun AssertionBdd.eligibility(eligibilityRepository: EligibilityRepository) = AssertionEligibility(eligibilityRepository)
//
//class AssertionEligibility(
//    override val repository: EligibilityRepository
//): AssertionPostgresEntity<EligibilityEntity, EligibilityId, AssertionEligibility.EligibilityAssert>() {
//
//    override fun assertThat(entity: EligibilityEntity) = EligibilityAssert(entity)
//
//    inner class EligibilityAssert(
//        private val eligibility: EligibilityEntity
//    ) {
//        fun hasFields(
//            id: EligibilityId = eligibility.id,
//            status: EligibilityState = eligibility.status,
//            legalCategory3Code: String = eligibility.legalCategory3Code,
//            condition: EligibilityCondition = eligibility.condition,
//            conditionData: Collection<String>? = eligibility.conditionData
//        ) = also {
//            Assertions.assertThat(eligibility.id).isEqualTo(id)
//            Assertions.assertThat(eligibility.status).isEqualTo(status)
//            Assertions.assertThat(eligibility.legalCategory3Code).isEqualTo(legalCategory3Code)
//            Assertions.assertThat(eligibility.condition).isEqualTo(condition)
//            Assertions.assertThat(eligibility.conditionData).containsExactlyInAnyOrderElementsOf(conditionData)
//        }
//    }
//}
