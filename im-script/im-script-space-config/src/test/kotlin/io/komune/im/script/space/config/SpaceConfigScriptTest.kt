package io.komune.im.script.space.config

import io.komune.im.apikey.lib.ApiKeyAggregateService
import io.komune.im.bdd.spring.SpringTestConfiguration
import io.komune.im.commons.auth.AuthContext
import io.komune.im.f2.organization.lib.OrganizationFinderService
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.space.config.config.ApiKeyData
import io.komune.im.script.space.config.config.OrganizationData
import io.komune.im.script.space.config.config.SpaceConfigProperties
import java.util.UUID
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SpaceConfigScriptTest: SpringTestConfiguration() {

    @Autowired
    lateinit var spaceConfigScript: SpaceConfigScript
    @Autowired
    lateinit var apiKeyAggregateService: ApiKeyAggregateService
    @Autowired
    lateinit var organizationFinderService: OrganizationFinderService
    @Autowired
    lateinit var imScriptSpaceProperties: ImScriptSpaceProperties

    @Test
    fun apiKeyOfOrganizationMustBeCreated(): Unit = runTest {
        val organizationName = "Komune-ApiKey-${UUID.randomUUID()}"
        val apiKeyName = "Apikeys-Test-${UUID.randomUUID()}"
        val data = SpaceConfigProperties(
            space = "im-test",
            organizations = listOf(
                OrganizationData(
                    name = organizationName,
                    apiKeys = listOf(
                        ApiKeyData(
                            name = apiKeyName,
                            secret = "secret"
                        )
                    )
                )
            )
        )
        val auth = imScriptSpaceProperties.auth.toAuthRealm(data.space)
        withContext(AuthContext(auth)) {
            spaceConfigScript.config(auth, data)

            val organizations = organizationFinderService.page(name = organizationName).items
            val organization = organizations.firstOrNull()
            val apiKey = organization?.id?.let { id ->
                apiKeyAggregateService.findByName(apiKeyName, id)
            }
            Assertions.assertThat(organization).isNotNull
            Assertions.assertThat(apiKey).isNotNull
        }
    }
}