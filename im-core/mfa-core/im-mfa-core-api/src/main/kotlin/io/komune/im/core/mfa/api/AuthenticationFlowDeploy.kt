package io.komune.im.core.mfa.api

import io.komune.im.core.mfa.domain.model.AuthenticationFlowDsl
import io.komune.im.core.mfa.domain.model.AuthenticationProvider
import io.komune.im.core.mfa.domain.model.Requirement
import io.komune.im.core.mfa.domain.model.SubFlow
import org.keycloak.admin.client.resource.AuthenticationManagementResource
import org.keycloak.representations.idm.AuthenticationFlowRepresentation
import org.keycloak.representations.idm.AuthenticatorConfigRepresentation

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
