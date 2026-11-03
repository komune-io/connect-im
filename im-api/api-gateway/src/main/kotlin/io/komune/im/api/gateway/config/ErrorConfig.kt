package io.komune.im.api.gateway.config

import f2.spring.exception.config.F2ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(F2ErrorWebExceptionHandler::class)
class ErrorConfig
