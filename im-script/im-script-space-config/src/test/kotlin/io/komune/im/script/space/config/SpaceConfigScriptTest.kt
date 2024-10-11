package io.komune.im.script.space.config

import io.komune.im.apikey.lib.ApiKeyAggregateService
import io.komune.im.bdd.spring.SpringTestConfiguration
import io.komune.im.commons.auth.AuthContext
import io.komune.im.f2.organization.lib.OrganizationFinderService
import io.komune.im.script.core.config.properties.ImAuthProperties
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.defaultSpaceRootClientId
import io.komune.im.script.space.config.config.ApiKeyData
import io.komune.im.script.space.config.config.OrganizationData
import io.komune.im.script.space.config.config.SpaceConfigProperties
import io.komune.im.script.space.create.ClientCredentials
import io.komune.im.script.space.create.SpaceCreateProperties
import io.komune.im.script.space.create.SpaceCreateScript
import java.util.UUID
import kotlin.math.absoluteValue
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SpaceConfigScriptTest: SpringTestConfiguration() {

    @Autowired
    lateinit var spaceCreateScript: SpaceCreateScript
    @Autowired
    lateinit var spaceConfigScript: SpaceConfigScript
    @Autowired
    lateinit var apiKeyAggregateService: ApiKeyAggregateService
    @Autowired
    lateinit var organizationFinderService: OrganizationFinderService
    @Autowired
    lateinit var imScriptSpaceProperties: ImScriptSpaceProperties

    val organizationName = "Komune-ApiKey-${UUID.randomUUID()}"
    val apiKeyName = "Apikeys-Test-${UUID.randomUUID()}"
    fun spaceConfig(spaceName: String) = SpaceConfigProperties(
        space = spaceName,
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

    @Test
    fun apiKeyOfOrganizationMustBeCreated(): Unit = runTest {
        val data = spaceConfig("im-test")
        val auth = imScriptSpaceProperties.auth.toAuthRealm(data.space)
        spaceConfigScript.config(auth, data)

        withContext(AuthContext(auth)) {
            val organizations = organizationFinderService.page(name = organizationName).items
            val organization = organizations.firstOrNull()
            val apiKey = organization?.id?.let { id ->
                apiKeyAggregateService.findByName(apiKeyName, id)
            }
            Assertions.assertThat(organization).isNotNull
            Assertions.assertThat(apiKey).isNotNull
        }
    }

    @Test
    fun mustBeConfigurableFromSpaceRootClientId(): Unit = runTest {
        val spaceName = "im-test-${UUID.randomUUID().toString().hashCode().absoluteValue}"

        val clientId = defaultSpaceRootClientId(spaceName)

        // Given a space
        val data = SpaceCreateProperties(
            space = spaceName,
            rootClient = ClientCredentials(
                clientId = clientId,
                clientSecret = "secret"
            )
        )
        spaceCreateScript.createScript(data)

        // When configuring the space with space root clientId
        val auth = ImAuthProperties(
            clientId = clientId,
            clientSecret = "secret",
            realmId = spaceName,
            serverUrl = imScriptSpaceProperties.auth.serverUrl
        ).toAuthRealm()


        val spaceConfig = spaceConfig(spaceName)
        spaceConfigScript.config(auth, spaceConfig)

        // Then the organization and apiKey must be created
        withContext(AuthContext(auth)) {
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
