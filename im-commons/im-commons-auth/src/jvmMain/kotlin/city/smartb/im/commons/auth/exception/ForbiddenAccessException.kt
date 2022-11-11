package city.smartb.im.commons.auth.exception

import f2.dsl.cqrs.error.F2Error
import f2.dsl.cqrs.exception.F2Exception
import java.time.LocalDate
import java.util.UUID
import org.springframework.http.HttpStatus

class ForbiddenAccessException(
    action: String
): F2Exception(
    error = F2Error(
        id = UUID.randomUUID().toString(),
        timestamp = LocalDate.now().toString(),
        message =  "You are not authorized to $action",
        code = HttpStatus.FORBIDDEN.value(),
    ),
)
