package city.smartb.im.test.app

import city.smartb.im.organization.client.OrganizationClient
import city.smartb.im.organization.domain.features.query.OrganizationRefGetAllQuery
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

@Service
class TestService(
    private val context: ConfigurableApplicationContext,
    private val organizationClient: OrganizationClient
): CommandLineRunner {
    override fun run(vararg args: String?) = runBlocking {
        val test = organizationClient.organizationRefGetAll(listOf(OrganizationRefGetAllQuery()))[0].items
        println(test.size)

        context.close()
    }
}
