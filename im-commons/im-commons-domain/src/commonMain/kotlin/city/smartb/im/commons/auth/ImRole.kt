package city.smartb.im.commons.auth

import kotlin.js.JsExport

@JsExport
enum class ImRole(val identifier: String) {
    ORCHESTRATOR("tr_orchestrator"),

    IM_USER_READ("im_read_user"),
    IM_USER_WRITE("im_write_user"),

    IM_ORGANIZATION_READ("im_read_organization"),
    IM_ORGANIZATION_WRITE("im_write_organization"),
    IM_MY_ORGANIZATION_WRITE("im_write_my_organization"),

    IM_APIKEY_READ("im_read_apikey"),

    IM_APIKEY_WRITE("im_write_apikey"),

    IM_SPACE_READ("im_space_read"),
    IM_SPACE_WRITE("im_space_write"),

    IM_ROLE_READ("im_read_role"),

    IM_ROLE_WRITE("im_write_role")
}
