package io.komune.im.f2.user.client

import f2.client.F2Client
import f2.client.domain.AuthRealm
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.http.plugin.F2Auth
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.komune.im.f2.user.domain.UserApi
import io.komune.im.f2.user.domain.command.UserCreateFunction
import io.komune.im.f2.user.domain.command.UserDeleteFunction
import io.komune.im.f2.user.domain.command.UserDisableFunction
import io.komune.im.f2.user.domain.command.UserResetPasswordFunction
import io.komune.im.f2.user.domain.command.UserUpdateEmailFunction
import io.komune.im.f2.user.domain.command.UserUpdateFunction
import io.komune.im.f2.user.domain.command.UserUpdatePasswordFunction
import io.komune.im.f2.user.domain.query.UserExistsByEmailFunction
import io.komune.im.f2.user.domain.query.UserGetByEmailFunction
import io.komune.im.f2.user.domain.query.UserGetFunction
import io.komune.im.f2.user.domain.query.UserPageFunction
import io.ktor.client.plugins.HttpTimeout
import kotlin.js.JsExport

fun F2Client.userClient(): F2SupplierSingle<UserClient> = f2SupplierSingle {
    UserClient(this)
}

fun userClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<UserClient> = f2SupplierSingle {
    UserClient(
        F2ClientBuilder.get(urlBase) {
            install(HttpTimeout) {
                @Suppress("MagicNumber")
                requestTimeoutMillis = 60000
            }
            install(F2Auth) {
                this.getAuth = getAuth
            }
        }
    )
}

@JsExport
open class UserClient(private val client: F2Client): UserApi {
    override fun userGet(): UserGetFunction = client.function(this::userGet.name)
    override fun userGetByEmail(): UserGetByEmailFunction = client.function(this::userGetByEmail.name)
    override fun userExistsByEmail(): UserExistsByEmailFunction = client.function(this::userExistsByEmail.name)
    override fun userPage(): UserPageFunction = client.function(this::userPage.name)

    override fun userCreate(): UserCreateFunction = client.function(this::userCreate.name)
    override fun userUpdate(): UserUpdateFunction = client.function(this::userUpdate.name)
    override fun userResetPassword(): UserResetPasswordFunction = client.function(this::userResetPassword.name)
    override fun userUpdateEmail(): UserUpdateEmailFunction = client.function(this::userUpdateEmail.name)
    override fun userUpdatePassword(): UserUpdatePasswordFunction = client.function(this::userUpdatePassword.name)
    override fun userDisable(): UserDisableFunction = client.function(this::userDisable.name)
    override fun userDelete(): UserDeleteFunction = client.function(this::userDelete.name)
}
