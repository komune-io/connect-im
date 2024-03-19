package io.komune.f2.spring.boot.auth.config

// properties
const val JWT_ISSUER_NAME = "f2.tenant.issuer-base-uri"

// conditional expressions
const val OPENID_REQUIRED_EXPRESSION = "!'\${$JWT_ISSUER_NAME:}'.isEmpty()"

const val AUTHENTICATION_REQUIRED_EXPRESSION = "($OPENID_REQUIRED_EXPRESSION)"
const val NO_AUTHENTICATION_REQUIRED_EXPRESSION = "!($AUTHENTICATION_REQUIRED_EXPRESSION)"
