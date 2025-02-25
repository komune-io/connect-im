package io.komune.im.f2.space.lib.flow

import org.keycloak.admin.client.resource.AuthenticationManagementResource
import org.keycloak.representations.idm.AuthenticationFlowRepresentation
import org.keycloak.representations.idm.AuthenticatorConfigRepresentation

enum class Requirement {
    REQUIRED, ALTERNATIVE, CONDITIONAL
}

enum class FlowType(val id: String) {
    BASIC_FLOW("basic-flow"),
}

enum class AuthenticationProvider(val id: String) {
    FORM_FLOW("registration-page-form"),
    USERNAME_PASSWORD("auth-username-password-form"),
    COOKIE("auth-cookie"),
    OTP_FORM("auth-otp-form"),
    ALLOW_ACCESS("allow-access-authenticator"),
    IDP_REDIRECT("identity-provider-redirector"),
    CONDITIONAL_USER_ATTRIBUTE("conditional-user-attribute"),
    CONDITIONAL_LEVEL_OF_AUTHENTICATION("conditional-level-of-authentication"),
    KERBEROS("auth-kerberos");

    override fun toString(): String = id
}

class AuthenticationFlowDsl(val alias: String) {
    var description: String? = null
    var type: FlowType = FlowType.BASIC_FLOW
    var isBuiltIn: Boolean = false
    var isTopLevel: Boolean = true
    val executions = mutableListOf<Execution>()
    val subFlows = mutableListOf<SubFlow>()

    fun execution(provider: AuthenticationProvider, requirement: Requirement, config: Map<String, String>? = null) {
        executions.add(Execution(provider, requirement, config))
    }

    fun subFlow(alias: String, type: FlowType, provider: AuthenticationProvider, block: SubFlow.() -> Unit) {
        val subFlow = SubFlow(alias, type, provider).apply(block)
        subFlows.add(subFlow)
    }
}

class SubFlow(val alias: String, var type: FlowType, val provider: AuthenticationProvider) {
    var description: String? = null
    var requirement: Requirement = Requirement.ALTERNATIVE
    val executions = mutableListOf<Execution>()
    val subFlows = mutableListOf<SubFlow>()

    fun execution(provider: AuthenticationProvider, requirement: Requirement, config: Map<String, String>? = null) {
        executions.add(Execution(provider, requirement, config))
    }

    fun condition(provider: AuthenticationProvider, block: Condition.() -> Unit) {
        val condition = Condition(provider).apply(block)
        executions.add(condition)
    }

    @Suppress("MemberNameEqualsClassName")
    fun subFlow(alias: String, type: FlowType, provider: AuthenticationProvider, block: SubFlow.() -> Unit) {
        val subFlow = SubFlow(alias, type, provider).apply(block)
        subFlows.add(subFlow)
    }
}

open class Execution(
    val provider: AuthenticationProvider,
    val requirement: Requirement,
    val config: Map<String, String>? = null
)

class Condition(provider: AuthenticationProvider) : Execution(provider, Requirement.REQUIRED, mutableMapOf()) {
    fun config(vararg pairs: Pair<String, String>) {
        (config as MutableMap).putAll(pairs)
    }
}

fun authenticationFlow(alias: String, block: AuthenticationFlowDsl.() -> Unit): AuthenticationFlowDsl {
    return AuthenticationFlowDsl(alias).apply(block)
}

fun AuthenticationFlowDsl.deploy(authFlowsClient: AuthenticationManagementResource) {
    val newFlow = AuthenticationFlowRepresentation().apply {
        this.alias = this@deploy.alias
        this.description = this@deploy.description ?: "Generated via Kotlin DSL"
        this.providerId = this@deploy.type.id
        this.isBuiltIn = this@deploy.isBuiltIn
        this.isTopLevel = this@deploy.isTopLevel
    }

    val dd = authFlowsClient.createFlow(newFlow)
    print(dd.status)
    // Add executions
    executions.forEach { execution ->
        authFlowsClient.addExecution(alias, execution.provider, execution.requirement, execution.config)
    }

    // Add subflows
    subFlows.forEach { subFlow ->
        deploySubFlow(authFlowsClient, alias, subFlow)
    }
}

private fun deploySubFlow(
    authFlowsClient: AuthenticationManagementResource,
    parentAlias: String,
    subFlow: SubFlow
) {
    authFlowsClient.addExecutionFlowLocal(parentAlias, subFlow)

    subFlow.executions.forEach { execution ->
        authFlowsClient.addExecution(subFlow.alias, execution.provider, execution.requirement, execution.config)
    }

    subFlow.subFlows.forEach { nestedSubFlow ->
        deploySubFlow(authFlowsClient, subFlow.alias, nestedSubFlow)
    }
}


private fun AuthenticationManagementResource.addExecution(
    parent: String,
    provider: AuthenticationProvider,
    requirement: Requirement,
    config: Map<String, String>? = null
) {
    addExecution(parent, mapOf("provider" to provider.id))

    val executions = getExecutions(parent)
    val executionToUpdate = executions.find { it.providerId == provider.id }
        ?: throw IllegalStateException("Execution not found for $provider")

    executionToUpdate.requirement = requirement.name
    updateExecutions(parent, executionToUpdate)

    if (config != null) {
        val configRep = AuthenticatorConfigRepresentation().apply {
            alias = "$parent-${provider.id}-config"
            this.config = config
        }
        newExecutionConfig(executionToUpdate.id, configRep)
    }
}

private fun AuthenticationManagementResource.addExecutionFlowLocal(
    parentName: String,
    subFlow: SubFlow,
) {
    addExecutionFlow(
        parentName, mapOf(
            SubFlow::alias.name to subFlow.alias,
            SubFlow::description.name to subFlow.description,
            SubFlow::provider.name to subFlow.provider.id,
            SubFlow::type.name to subFlow.type.id,
        )
    )

    // Step 2: Fetch Executions to Get Execution ID
    val executions = getExecutions(parentName)

    // Find the execution ID for "auth-cookie"
    val executionToUpdate = executions.find { it.displayName == subFlow.alias }
        ?: throw IllegalStateException("Execution not found for auth-cookie")

    println("Found execution ID: ${executionToUpdate.id}")

    // Step 3: Update Execution Requirement
    executionToUpdate.requirement = subFlow.requirement.name
    this.updateExecutions(parentName, executionToUpdate)
}
