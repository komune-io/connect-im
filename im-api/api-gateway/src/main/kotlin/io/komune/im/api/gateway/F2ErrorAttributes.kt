package io.komune.im.api.gateway

import f2.dsl.cqrs.error.F2Error
import f2.dsl.cqrs.exception.F2Exception
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.web.reactive.function.server.ServerRequest

@Deprecated("Delete with new fixers version")
class F2ErrorAttributes: DefaultErrorAttributes() {
    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val attributes = super.getErrorAttributes(request, options.including(ErrorAttributeOptions.Include.MESSAGE))
        val exception = getError(request)

        val f2Exception = exception.takeIf { exception is F2Exception }
            ?: exception.cause.takeIf { exception.cause is F2Exception }

        if (f2Exception is F2Exception) {
            attributes[F2Error::id.name] = f2Exception.error.id
            attributes[F2Error::code.name] = f2Exception.error.code
            attributes[F2Error::message.name] = f2Exception.error.message
            attributes[F2Error::timestamp.name] = f2Exception.error.timestamp
        }
        return attributes
    }
}
