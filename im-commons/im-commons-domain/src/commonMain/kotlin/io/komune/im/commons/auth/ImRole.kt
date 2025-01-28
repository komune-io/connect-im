package io.komune.im.commons.auth

import kotlin.js.JsExport

@JsExport
enum class ImRole(val identifier: String) {
    ORCHESTRATOR("tr_orchestrator"),
    ORCHESTRATOR_ADMIN("tr_orchestrator_admin"),
    ORCHESTRATOR_USER("tr_orchestrator_user"),

    IM_USER_READ("im_user_read"),
    IM_USER_ROLE_READ("im_user_role_write"),
    IM_USER_WRITE("im_user_write"),

    IM_ORGANIZATION_READ("im_organization_read"),
    IM_ORGANIZATION_WRITE("im_organization_write"),
    IM_MY_ORGANIZATION_WRITE("im_organization_write_own"),

    IM_APIKEY_READ("im_apikey_read"),
    IM_APIKEY_WRITE("im_apikey_write"),

    IM_SPACE_READ("im_space_read"),
    IM_SPACE_WRITE("im_space_write"),

    IM_ROLE_READ("im_role_read"),
    IM_ROLE_WRITE("im_role_write")
}
