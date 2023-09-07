package city.smartb.im.core.privilege.api.exception

import city.smartb.im.commons.ExceptionCodes
import city.smartb.im.commons.model.PrivilegeIdentifier
import city.smartb.im.core.privilege.domain.model.RoleTarget
import f2.spring.exception.F2HttpException
import org.springframework.http.HttpStatus

class PrivilegeWrongTargetException(privilege: PrivilegeIdentifier, target: RoleTarget): F2HttpException(
    status = HttpStatus.INTERNAL_SERVER_ERROR,
    code = ExceptionCodes.privilegeWrongTarget(),
    message = "Privilege [$privilege] cannot be applied to target [$target]",
    cause = null
)
