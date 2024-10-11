package io.komune.im.script.space.config

import io.komune.im.bdd.spring.SpringTestConfiguration
import io.komune.im.commons.auth.AuthContext
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.defaultSpaceRootClientId
import io.komune.im.script.core.service.ClientInitService
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

class SpaceCreateScriptTest: SpringTestConfiguration() {

    @Autowired
    lateinit var spaceCreateScript: SpaceCreateScript
    @Autowired
    lateinit var imScriptSpaceProperties: ImScriptSpaceProperties

    @Autowired
    lateinit var clientInitService: ClientInitService

    @Test
    fun spaceRootClientMustBeCreated(): Unit = runTest {
        val spaceName = "im-test-${UUID.randomUUID().hashCode().absoluteValue}"
        val clientId = defaultSpaceRootClientId(spaceName)
        val data = SpaceCreateProperties(
            space = spaceName,
            rootClient = ClientCredentials(
                clientId = clientId,
                clientSecret = "secret"
            )
        )

        spaceCreateScript.createScript(data)

        val newRealmAuth = imScriptSpaceProperties.auth.toAuthRealm(data.space)
        withContext(AuthContext(newRealmAuth)) {
            val client = clientInitService.getAppClientId(clientId)

            Assertions.assertThat(client).isNotNull
                .describedAs("Client $clientId must be created")

        }
    }
}
