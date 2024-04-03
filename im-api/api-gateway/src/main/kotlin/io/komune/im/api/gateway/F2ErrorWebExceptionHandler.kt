package io.komune.im.api.gateway

import f2.spring.exception.F2HttpException
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@Suppress("MagicNumber")
@Order(Ordered.HIGHEST_PRECEDENCE)
@Deprecated("Delete with new fixers version")
class F2ErrorWebExceptionHandler(
    applicationContext: ApplicationContext,
    webProperties: WebProperties,
    serverCodecConfigurer: ServerCodecConfigurer,
    viewResolvers: ObjectProvider<ViewResolver>,
    serverProperties: ServerProperties,
): DefaultErrorWebExceptionHandler(
    F2ErrorAttributes(),
    webProperties.resources,
    serverProperties.error,
    applicationContext
) {
    companion object {
        private const val INTERNAL_ERROR = 500
    }
    init {
        setViewResolvers(viewResolvers.toList())
        setMessageWriters(serverCodecConfigurer.writers)
        setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return super.getRoutingFunction(errorAttributes)
    }

    override fun handle(exchange: ServerWebExchange, throwable: Throwable): Mono<Void> {
        val f2Cause = throwable.takeIf { throwable is F2HttpException }
            ?: throwable.cause.takeIf { throwable.cause is F2HttpException }
        if (f2Cause is F2HttpException) {
            val status = f2Cause.error.code.takeIf { it in 400..500 } ?: 500
            return super.handle(exchange, ResponseStatusException(status, f2Cause.message, f2Cause))
        }
        return super.handle(exchange, throwable)
    }
}
