package city.smartb.im.role.domain

import city.smartb.im.role.domain.features.command.RoleAddCompositesFunction
import city.smartb.im.role.domain.features.command.RoleCreateFunction
import city.smartb.im.role.domain.features.command.RoleUpdateFunction

/**
 * @d2 service
 */
interface RoleCommandFeatures {
    fun roleAddComposites(): RoleAddCompositesFunction
    fun roleCreate(): RoleCreateFunction
    fun roleUpdate(): RoleUpdateFunction
}
