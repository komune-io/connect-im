package i2.test.it.group

import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.test.bdd.assertion.AssertionKC
import i2.test.bdd.assertion.group
import i2.test.bdd.data.DataTest
import i2.test.bdd.data.group.groupCreateCommand
import i2.test.bdd.given.GivenKC
import i2.test.bdd.given.auth
import i2.test.bdd.given.group
import i2.test.bdd.given.realm
import i2.test.bdd.testcontainers.I2KeycloakTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class GroupCreateFunctionImplTest: I2KeycloakTest() {
    private val clientMaster = GivenKC().auth().withMasterRealmClient()
    private val realmId = GivenKC(clientMaster).realm().withTestRealm()

    @Autowired
    private lateinit var groupCreateFunction: GroupCreateFunction

    @Test
    fun `should create group`(): Unit = runBlocking {
        val groupUuid = "group-${UUID.randomUUID()}"
        val cmd = DataTest.groupCreateCommand(
            realmId = realmId,
            auth = clientMaster.auth,
            name = "name-${groupUuid}",
            attributes = mapOf("zeKey" to "zeValue")
        )
        val event = groupCreateFunction.invoke(cmd)

        Assertions.assertThat(event.id).isNotNull
        AssertionKC.group(clientMaster.keycloak).assertThat(realmId, event.id).hasFields(
            id = event.id,
            name = cmd.name,
            realmRoles = cmd.roles,
            attributes = cmd.attributes,
        )
    }

    @Test
    fun `should create sub group`(): Unit = runBlocking {
        val groupUuid = "group-${UUID.randomUUID()}"
        val parentGroupId = GivenKC().group().withGroup(realmId, groupUuid)
        val cmd = DataTest.groupCreateCommand(
            realmId = realmId,
            auth = clientMaster.auth,
            name = "name-${groupUuid}",
            attributes = mapOf("zeKey" to "zeValue"),
            parentGroupId = parentGroupId
        )
        val event = groupCreateFunction.invoke(cmd)

        Assertions.assertThat(event.id).isNotNull
        AssertionKC.group(clientMaster.keycloak).assertThat(realmId, event.id).hasFields(
            id = event.id,
            name = cmd.name,
            realmRoles = cmd.roles,
            attributes = cmd.attributes,
        ).haveParentGroup(parentGroupId)
    }
}
