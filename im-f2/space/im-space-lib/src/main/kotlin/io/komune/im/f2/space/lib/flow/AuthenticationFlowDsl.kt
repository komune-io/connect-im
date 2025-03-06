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
    CONDITIONAL_USER_ROLE("conditional-user-role"),
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

    fun execution(block: Execution.() -> Unit) {
        executions.add(Execution().apply(block))
    }

    fun subFlow(block: SubFlow.() -> Unit) {
        val subFlow = SubFlow().apply(block)
        subFlows.add(subFlow)
    }
}

class SubFlow {
    lateinit var alias: String
    var type: FlowType = FlowType.BASIC_FLOW
    lateinit var provider: AuthenticationProvider
    var description: String? = null
    var requirement: Requirement = Requirement.ALTERNATIVE
    val executions = mutableListOf<Execution>()
    val subFlows = mutableListOf<SubFlow>()

    fun execution(block: Execution.() -> Unit) {
        executions.add(Execution().apply(block))
    }

    fun conditional(block: Conditional.() -> Unit) {
        val conditional = Conditional().apply(block)
        executions.add(conditional)
    }

    fun conditionalLoa(block: ConditionalLoa.() -> Unit) {
        val conditionalLoa = ConditionalLoa().apply(block)
        executions.add(conditionalLoa)
    }

    @Suppress("MemberNameEqualsClassName")
    fun subFlow(block: SubFlow.() -> Unit) {
        val subFlow = SubFlow().apply(block)
        subFlows.add(subFlow)
    }
}

open class Execution {
    lateinit var provider: AuthenticationProvider
    var requirement: Requirement = Requirement.REQUIRED
    open var config: Map<String, String>? = null
}

class Conditional : Execution() {
    fun config(vararg pairs: Pair<String, String>) {
        this.config = pairs.toMap()
    }
}

class ConditionalLoa : Execution() {
    var loaConditionLevel: Int = 0
    var loaMaxAge: Int = 0

    init {
        provider = AuthenticationProvider.CONDITIONAL_LEVEL_OF_AUTHENTICATION
    }

    fun buildConfig(): Map<String, String> {
        return mapOf(
            "loa-condition-level" to loaConditionLevel.toString(),
            "loa-max-age" to loaMaxAge.toString()
        )
    }

    override var config: Map<String, String>?
        get() = buildConfig()
        set(_) {}
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

    executions.forEach { execution ->
        authFlowsClient.addExecution(alias, execution.provider, execution.requirement, execution.config)
    }

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
            "alias" to subFlow.alias,
            "description" to subFlow.description,
            "provider" to subFlow.provider.id,
            "type" to subFlow.type.id,
        )
    )

    val executions = getExecutions(parentName)

    val executionToUpdate = executions.find { it.displayName == subFlow.alias }
        ?: throw IllegalStateException("Execution not found for ${subFlow.alias}")

    executionToUpdate.requirement = subFlow.requirement.name
    this.updateExecutions(parentName, executionToUpdate)
}
