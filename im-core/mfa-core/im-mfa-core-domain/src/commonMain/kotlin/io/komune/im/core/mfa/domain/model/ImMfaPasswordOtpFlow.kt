package io.komune.im.core.mfa.domain.model

import io.komune.im.commons.auth.ImPermission

object ImMfaPasswordOtpFlow {

    const val name = "browser-im-mfa-password-otp"

    enum class Acr(val key: String, val level: Int) {
        PASSWORD_ONLY(key = "password-only", level = 1),
        PASSWORD_OTP(key = "password-otp", level = 2);

        companion object {
            fun asKeycloakMap() = entries.map { it.key to it.level.toString() }.toMap()
        }
    }

    val flow = authenticationFlow(name) {
        description = "Custom browser flow with password authentication and conditional OTP"
        type = FlowType.BASIC_FLOW
        isBuiltIn = false
        isTopLevel = true

        execution {
            provider = AuthenticationProvider.COOKIE
            requirement = Requirement.ALTERNATIVE
        }

        execution {
            provider = AuthenticationProvider.IDP_REDIRECT
            requirement = Requirement.ALTERNATIVE
        }

        subFlow("login-with-username-password") {
            type = FlowType.BASIC_FLOW
            provider = AuthenticationProvider.FORM_FLOW

            // loa-password-only: Simple authentication (password only)
            subFlow("loa-password-only") {
                type = FlowType.BASIC_FLOW
                provider = AuthenticationProvider.FORM_FLOW
                requirement = Requirement.CONDITIONAL

                conditionalLoa {
                    loaConditionLevel = Acr.PASSWORD_ONLY.level
                    loaMaxAge = 0
                }

                execution {
                    provider = AuthenticationProvider.USERNAME_PASSWORD
                    requirement = Requirement.REQUIRED
                }
            }

            // loa-password-otp: Strict MFA enforcement (always require OTP)
            subFlow("loa-password-otp") {
                type = FlowType.BASIC_FLOW
                provider = AuthenticationProvider.FORM_FLOW
                requirement = Requirement.CONDITIONAL

                conditionalLoa {
                    loaConditionLevel = Acr.PASSWORD_OTP.level
                    loaMaxAge = 0
                }

                execution {
                    provider = AuthenticationProvider.OTP_FORM
                    requirement = Requirement.REQUIRED
                }
            }

            // otp-form: OTP form
            subFlow("login-with-conditional-otp") {
                type = FlowType.BASIC_FLOW
                provider = AuthenticationProvider.FORM_FLOW
                requirement = Requirement.CONDITIONAL

                conditional {
                    provider = AuthenticationProvider.CONDITIONAL_SUB_FLOW_EXECUTED
                    requirement = Requirement.REQUIRED
                    config(
                        "check_result" to "not-executed",
                        "default.reference.maxAge" to "",
                        "default.reference.value" to "",
                        "flow_to_check"  to "loa-password-otp"
                    )
                }

                subFlow("login-with-conditional-otp-conditional") {
                    type = FlowType.BASIC_FLOW
                    provider = AuthenticationProvider.FORM_FLOW
                    requirement = Requirement.CONDITIONAL

                    conditional {
                        provider = AuthenticationProvider.CONDITIONAL_USER_CONFIGURED
                        requirement = Requirement.REQUIRED
                    }

                    conditional {
                        provider = AuthenticationProvider.CONDITIONAL_SUB_FLOW_EXECUTED
                        requirement = Requirement.REQUIRED
                        config(
                            "check_result" to "not-executed",
                            "default.reference.maxAge" to "",
                            "default.reference.value" to "",
                            "flow_to_check"  to "force-mfa-user-role-${ImPermission.IM_FORCE_MFA_OTP.identifier}"
                        )
                    }

                    execution {
                        provider = AuthenticationProvider.OTP_FORM
                        requirement = Requirement.REQUIRED
                    }
                }
                subFlow("force-mfa-user-role-${ImPermission.IM_FORCE_MFA_OTP.identifier}") {
                    type = FlowType.BASIC_FLOW
                    provider = AuthenticationProvider.FORM_FLOW
                    requirement = Requirement.CONDITIONAL

                    conditional {
                        provider = AuthenticationProvider.CONDITIONAL_USER_ROLE
                        config(
                            "role" to ImPermission.IM_FORCE_MFA_OTP.identifier,
                            "condUserRole" to ImPermission.IM_FORCE_MFA_OTP.identifier
                        )
                    }

                    execution {
                        provider = AuthenticationProvider.OTP_FORM
                        requirement = Requirement.REQUIRED
                    }

                }

                execution {
                    provider = AuthenticationProvider.ALLOW_ACCESS
                    requirement = Requirement.REQUIRED
                }
            }
        }
    }
}
