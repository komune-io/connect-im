package io.komune.im.core.mfa.domain.model

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
    RESET_PASSWORD("reset-password"),
    RESET_CREDENTIALS_CHOOSE_USER("reset-credentials-choose-user"),
    RESET_CREDENTIALS_EMAIL("reset-credential-email"),
    OTP_FORM("auth-otp-form"),
    ALLOW_ACCESS("allow-access-authenticator"),
    IDP_REDIRECT("identity-provider-redirector"),
    CONDITIONAL_USER_ATTRIBUTE("conditional-user-attribute"),
    CONDITIONAL_USER_ROLE("conditional-user-role"),
    CONDITIONAL_USER_CONFIGURED("conditional-user-configured"),
    CONDITIONAL_LEVEL_OF_AUTHENTICATION("conditional-level-of-authentication"),
    CONDITIONAL_SUB_FLOW_EXECUTED("conditional-sub-flow-executed"),
    KERBEROS("auth-kerberos");

    override fun toString(): String = id
}

interface FlowObject

class AuthenticationFlowDsl(val alias: String) {
    var description: String? = null
    var type: FlowType = FlowType.BASIC_FLOW
    var isBuiltIn: Boolean = false
    var isTopLevel: Boolean = true
    val subObjects = arrayListOf<FlowObject>()

    fun execution(block: Execution.() -> Unit) {
        subObjects.add(Execution().apply(block))
    }

    fun subFlow(alias: String, block: SubFlow.() -> Unit) {
        val subFlow = SubFlow(alias).apply(block)
        subObjects.add(subFlow)
    }
}


class SubFlow(val alias: String): FlowObject {
    var type: FlowType = FlowType.BASIC_FLOW
    lateinit var provider: AuthenticationProvider
    var description: String? = null
    var requirement: Requirement = Requirement.ALTERNATIVE
    val subObjects = arrayListOf<FlowObject>()

    fun execution(block: Execution.() -> Unit) {
        subObjects.add(Execution().apply(block))
    }

    fun conditional(block: ConditionalExecution.() -> Unit) {
        val conditional = ConditionalExecution().apply(block)
        subObjects.add(conditional)
    }

    fun conditionalLoa(block: ConditionalLoa.() -> Unit) {
        val conditionalLoa = ConditionalLoa().apply(block)
        subObjects.add(conditionalLoa)
    }

    @Suppress("MemberNameEqualsClassName")
    fun subFlow(alias: String, block: SubFlow.() -> Unit) {
        val subFlow = SubFlow(alias).apply(block)
        subObjects.add(subFlow)
    }

}

open class Execution: FlowObject {
    lateinit var provider: AuthenticationProvider
    var requirement: Requirement = Requirement.REQUIRED
    open var config: Map<String, String>? = null
}

class ConditionalExecution : Execution() {
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
