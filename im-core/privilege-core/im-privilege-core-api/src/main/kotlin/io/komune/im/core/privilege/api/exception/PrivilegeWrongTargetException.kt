package io.komune.im.core.privilege.api.exception

import io.komune.im.commons.exception.ExceptionCodes
import io.komune.im.commons.model.PrivilegeIdentifier
import io.komune.im.core.privilege.domain.model.RoleTarget
import f2.spring.exception.F2HttpException
import org.springframework.http.HttpStatus

class PrivilegeWrongTargetException(privilege: PrivilegeIdentifier, target: RoleTarget): F2HttpException(
    status = HttpStatus.INTERNAL_SERVER_ERROR,
    code = ExceptionCodes.privilegeWrongTarget(),
    message = "Privilege [$privilege] cannot be applied to target [$target]",
    cause = null
)
