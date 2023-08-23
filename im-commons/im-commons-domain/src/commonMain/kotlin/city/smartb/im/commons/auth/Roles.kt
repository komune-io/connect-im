package city.smartb.im.commons.auth

import kotlin.js.JsExport

@JsExport
object Roles {
    const val SUPER_ADMIN = "super_admin"
    const val ORCHESTRATOR = "tr_orchestrator"

    const val IM_USER_READ = "im_read_user"
    const val IM_USER_WRITE = "im_write_user"

    const val IM_ORGANIZATION_READ = "im_read_organization"
    const val IM_ORGANIZATION_WRITE = "im_write_organization"

    const val IM_APIKEY_READ = "im_read_apikey"
    const val IM_APIKEY_WRITE = "im_write_apikey"

    const val IM_ROLE_READ = "im_read_role"
    const val IM_ROLE_WRITE = "im_write_role"

    const val IM_MY_ORGANIZATION_WRITE = "im_write_my_organization"
}
