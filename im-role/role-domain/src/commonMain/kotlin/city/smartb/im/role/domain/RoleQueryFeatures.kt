package city.smartb.im.role.domain

import city.smartb.im.role.domain.features.query.RoleGetByIdFunction
import city.smartb.im.role.domain.features.query.RoleGetByNameFunction

interface RoleQueryFeatures {
    fun roleGetById(): RoleGetByIdFunction
    fun roleGetByName(): RoleGetByNameFunction
}
