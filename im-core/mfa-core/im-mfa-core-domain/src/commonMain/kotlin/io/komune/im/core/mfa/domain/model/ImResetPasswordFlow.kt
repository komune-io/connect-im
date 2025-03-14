package io.komune.im.core.mfa.domain.model

object ImResetPasswordFlow {

    const val name = "browser-im-reset-password"

    val flow = authenticationFlow(name) {
        description = " Reset credentials flow for resetting password"
        type = FlowType.BASIC_FLOW
        isBuiltIn = false
        isTopLevel = true


        execution {
            provider = AuthenticationProvider.RESET_CREDENTIALS_CHOOSE_USER
            requirement = Requirement.REQUIRED
        }

        execution {
            provider = AuthenticationProvider.RESET_CREDENTIALS_EMAIL
            requirement = Requirement.REQUIRED
        }

        execution {
            provider = AuthenticationProvider.RESET_PASSWORD
            requirement = Requirement.REQUIRED
        }

    }
}
