package io.komune.im.api.gateway.config

import f2.spring.exception.config.F2ErrorWebExceptionHandler
import f2.spring.exception.findF2Exception
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.result.view.ViewResolver

@Component
@Order(ImErrorWebExceptionHandler.ORDER)
class ImErrorWebExceptionHandler(
    applicationContext: ApplicationContext,
    webProperties: WebProperties,
    serverCodecConfigurer: ServerCodecConfigurer,
    viewResolvers: ObjectProvider<ViewResolver>,
) : F2ErrorWebExceptionHandler(applicationContext, webProperties, serverCodecConfigurer, viewResolvers) {

    // F2ErrorAttributes always includes the message; only an F2 error's message is meant for the caller.
    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): Map<String, Any?> {
        val attributes = super.getErrorAttributes(request, options)
        return if (getError(request).findF2Exception() != null) attributes else attributes - MESSAGE
    }

    companion object {
        // Ahead of Spring Boot's DefaultErrorWebExceptionHandler (-1).
        const val ORDER = -2
        private const val MESSAGE = "message"
    }
}
