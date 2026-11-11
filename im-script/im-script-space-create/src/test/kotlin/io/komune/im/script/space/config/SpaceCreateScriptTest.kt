package io.komune.im.script.space.config

import io.komune.im.bdd.spring.SpringTestConfiguration
import io.komune.im.commons.auth.AuthContext
import io.komune.im.infra.keycloak.client.KeycloakClientProvider
import io.komune.im.script.core.config.properties.ImScriptSpaceProperties
import io.komune.im.script.core.config.properties.toAuthRealm
import io.komune.im.script.core.model.defaultSpaceRootClientId
import io.komune.im.script.core.service.ClientInitService
import io.komune.im.script.space.create.SpaceCreateScript
import io.komune.im.script.space.create.config.ClientCredentials
import io.komune.im.script.space.create.config.SpaceCreateProperties
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

    @Autowired
    lateinit var keycloakClientProvider: KeycloakClientProvider

    @Test
    fun sslRequiredMustBeAppliedToCreatedSpace() = runTest {
        val spaceName = "im-test-${UUID.randomUUID().hashCode().absoluteValue}"
        val clientId = defaultSpaceRootClientId(spaceName)
        val data = SpaceCreateProperties(
            identifier = spaceName,
            sslRequired = "NONE",
            rootClient = ClientCredentials(
                clientId = clientId,
                clientSecret = "secret"
            )
        )

        spaceCreateScript.createScript(data)

        val masterAuth = imScriptSpaceProperties.auth.toAuthRealm()
        withContext(AuthContext(masterAuth)) {
            val client = keycloakClientProvider.getClient()
            val realm = client.realm(spaceName).toRepresentation()
            Assertions.assertThat(realm.sslRequired).isEqualToIgnoringCase("NONE")
        }
    }

    @Test
    fun spaceRootClientMustBeCreated() = runTest {
        val spaceName = "im-test-${UUID.randomUUID().hashCode().absoluteValue}"
        val clientId = defaultSpaceRootClientId(spaceName)
        val data = SpaceCreateProperties(
            identifier = spaceName,
            rootClient = ClientCredentials(
                clientId = clientId,
                clientSecret = "secret"
            )
        )

        spaceCreateScript.createScript(data)

        val newRealmAuth = imScriptSpaceProperties.auth.toAuthRealm(data.spaceIdentifier)
        withContext(AuthContext(newRealmAuth)) {
            val client = clientInitService.getAppClientId(clientId)

            Assertions.assertThat(client).isNotNull
                .describedAs("Client $clientId must be created")

        }
    }
    @Test
    fun defaultRoleMustHoldTheSelfPermissionsOnNewAndExistingSpaces() = runTest {
        val spaceName = "im-test-${UUID.randomUUID().hashCode().absoluteValue}"
        val data = SpaceCreateProperties(identifier = spaceName)
        val selfPermissions = listOf("im_user_self", "im_organization_read_own")

        spaceCreateScript.createScript(data)

        val newRealmAuth = imScriptSpaceProperties.auth.toAuthRealm(data.spaceIdentifier)
        withContext(AuthContext(newRealmAuth)) {
            val client = keycloakClientProvider.getClient()
            val defaultRole = client.role(client.defaultRealmRole)
            Assertions.assertThat(defaultRole.realmRoleComposites.map { it.name })
                .describedAs("A new space grants the self permissions by default")
                .containsAll(selfPermissions)

            // An existing space provisioned before these permissions existed
            defaultRole.deleteComposites(defaultRole.realmRoleComposites.filter { it.name in selfPermissions })
        }

        spaceCreateScript.createScript(data)

        withContext(AuthContext(newRealmAuth)) {
            val client = keycloakClientProvider.getClient()
            Assertions.assertThat(client.role(client.defaultRealmRole).realmRoleComposites.map { it.name })
                .describedAs("Re-running the script on an existing space adds what is missing")
                .containsAll(selfPermissions)
        }
    }
}
