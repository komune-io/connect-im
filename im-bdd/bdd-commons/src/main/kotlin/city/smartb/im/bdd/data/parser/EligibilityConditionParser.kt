//package city.smartb.im.bdd.data.parser
//
//import city.smartb.im.s2.eligibility.domain.model.EligibilityCondition
//import kotlin.reflect.jvm.jvmName
//
//private val eligibilityConditionParser = EntryParser(
//    parseErrorMessage = "Eligibility Condition must be in ${EligibilityCondition::class.jvmName} values",
//    parser = String::toEligibilityCondition
//)
//
//fun Map<String, String>.extractEligibilityCondition(key: String) = eligibilityConditionParser.single(this, key)
//fun Map<String, String>.extractEligibilityConditionList(key: String) = eligibilityConditionParser.list(this, key)
//
//fun String.toEligibilityCondition() = EligibilityCondition.valueOf(this)
