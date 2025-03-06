package io.komune.im.commons.auth

import kotlin.js.JsExport

@JsExport
enum class ImPermission(val identifier: String) {
    IM_FORCE_MFA_OTP("im_mfa_force_otp"),

    IM_USER_READ("im_user_read"),
    IM_USER_ROLE_READ("im_user_role_write"),
    IM_USER_WRITE("im_user_write"),

    IM_ORGANIZATION_READ("im_organization_read"),
    IM_ORGANIZATION_WRITE("im_organization_write"),
    IM_MY_ORGANIZATION_WRITE("im_organization_write_own"),

    IM_ORGANIZATION_API_KEY_READ("im_organization_apikey_read"),
    IM_ORGANIZATION_STATUS_WRITE("im_organization_status_write"),

    IM_APIKEY_READ("im_apikey_read"),
    IM_APIKEY_WRITE("im_apikey_write"),

    IM_SPACE_READ("im_space_read"),
    IM_SPACE_WRITE("im_space_write"),

    IM_ROLE_READ("im_role_read"),
    IM_ROLE_WRITE("im_role_write")
}
