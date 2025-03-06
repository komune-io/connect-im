package io.komune.im.f2.space.lib.flow

import io.komune.im.commons.auth.ImPermission


object ImMfaPasswordOtpFlow {

    const val name = "browser-im-mfa-password-otp"

    enum class Acr(val key: String, val level: Int) {
        PASSWORD_ONLY(key = "password-only", level = 1),
        PASSWORD_OPTIONAL_OTP(key = "password-optional-otp", level = 2),
        PASSWORD_OTP(key = "password-otp", level = 3);

        companion object {
            fun asKeycloakMap() = entries.map { it.key to it.level.toString() }.toMap()
        }
    }

    val flow = authenticationFlow(ImMfaPasswordOtpFlow.name) {
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

        subFlow {
            alias = "username-password"
            type = FlowType.BASIC_FLOW
            provider = AuthenticationProvider.FORM_FLOW

            execution {
                provider = AuthenticationProvider.USERNAME_PASSWORD
                requirement = Requirement.REQUIRED
            }

            // password-only: Simple authentication (password only)
            subFlow {
                alias = "loa-password-only"
                type = FlowType.BASIC_FLOW
                provider = AuthenticationProvider.FORM_FLOW
                requirement = Requirement.CONDITIONAL

                conditionalLoa {
                    loaConditionLevel = ImMfaPasswordOtpFlow.Acr.PASSWORD_ONLY.level
                    loaMaxAge = 0
                }

                execution {
                    provider = AuthenticationProvider.ALLOW_ACCESS
                    requirement = Requirement.REQUIRED
                }
            }

            // password-optional-otp: Conditional MFA (OTP if required)
            subFlow {
                alias = "loa-password-optional-otp"
                type = FlowType.BASIC_FLOW
                provider = AuthenticationProvider.FORM_FLOW
                requirement = Requirement.CONDITIONAL

                conditionalLoa {
                    loaConditionLevel = ImMfaPasswordOtpFlow.Acr.PASSWORD_OPTIONAL_OTP.level
                    loaMaxAge = 0
                }

                execution {
                    provider = AuthenticationProvider.ALLOW_ACCESS
                    requirement = Requirement.REQUIRED
                }
            }

            // password-otp: Strict MFA enforcement (always require OTP)
            subFlow {
                alias = "loa-password-otp"
                type = FlowType.BASIC_FLOW
                provider = AuthenticationProvider.FORM_FLOW
                requirement = Requirement.CONDITIONAL

                conditionalLoa {
                    loaConditionLevel = ImMfaPasswordOtpFlow.Acr.PASSWORD_OTP.level
                    loaMaxAge = 0
                }

                execution {
                    provider = AuthenticationProvider.OTP_FORM
                    requirement = Requirement.REQUIRED
                }
            }

            // Force OTP for users with the "force-mfa" role
            subFlow {
                alias = "force-mfa-user-role-${ImPermission.IM_FORCE_MFA_OTP.identifier}"
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
        }
    }
}
