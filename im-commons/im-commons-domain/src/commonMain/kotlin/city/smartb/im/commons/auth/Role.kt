package city.smartb.im.commons.auth

enum class Role(val value: String) {
    IM_USER_READ("im_read_user"),
    IM_USER_WRITE("im_write_user"),

    IM_ORGANIZATION_READ("im_read_organization"),
    IM_ORGANIZATION_WRITE("im_write_organization"),

    IM_ROLE_READ("im_read_role"),
    IM_ROLE_WRITE("im_write_role"),

    IM_MY_ORGANIZATION_WRITE("im_write_my_organization"),
}
