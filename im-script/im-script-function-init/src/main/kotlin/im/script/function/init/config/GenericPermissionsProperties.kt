package im.script.function.init.config

import im.script.function.core.model.PermissionData

data class GenericPermissionsProperties(
    val superAdmin: PermissionData,
    val im: List<PermissionData>,
    val fs: List<PermissionData>
)
