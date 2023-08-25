package city.smartb.im.bdd

import org.springframework.beans.factory.annotation.Autowired

open class ImCucumberStepsDefinition: s2.bdd.CucumberStepsDefinition() {

    @Autowired
    override lateinit var context: ImTestContext
}
