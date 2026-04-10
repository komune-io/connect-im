package io.komune.im.f2.user.client

import f2.client.F2Client
import f2.client.domain.AuthRealm
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.http.plugin.F2Auth
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.komune.im.f2.user.domain.UserApi
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.f2.user.domain.command.UserCreateFunction
import io.komune.im.f2.user.domain.command.UserCreatedEvent
import io.komune.im.f2.user.domain.command.UserDeleteCommand
import io.komune.im.f2.user.domain.command.UserDeleteFunction
import io.komune.im.f2.user.domain.command.UserDeletedEvent
import io.komune.im.f2.user.domain.command.UserDisableCommand
import io.komune.im.f2.user.domain.command.UserDisableFunction
import io.komune.im.f2.user.domain.command.UserDisabledEvent
import io.komune.im.f2.user.domain.command.UserDisableMfaCommand
import io.komune.im.f2.user.domain.command.UserDisableMfaFunction
import io.komune.im.f2.user.domain.command.UserDisabledMfavent
import io.komune.im.f2.user.domain.command.UserEnableCommand
import io.komune.im.f2.user.domain.command.UserEnableFunction
import io.komune.im.f2.user.domain.command.UserEnabledEvent
import io.komune.im.f2.user.domain.command.UserResetPasswordCommand
import io.komune.im.f2.user.domain.command.UserResetPasswordEvent
import io.komune.im.f2.user.domain.command.UserResetPasswordFunction
import io.komune.im.f2.user.domain.command.UserUpdateCommand
import io.komune.im.f2.user.domain.command.UserUpdateEmailCommand
import io.komune.im.f2.user.domain.command.UserUpdateEmailFunction
import io.komune.im.f2.user.domain.command.UserUpdateFunction
import io.komune.im.f2.user.domain.command.UserUpdatePasswordCommand
import io.komune.im.f2.user.domain.command.UserUpdatePasswordFunction
import io.komune.im.f2.user.domain.command.UserUpdatedEmailEvent
import io.komune.im.f2.user.domain.command.UserUpdatedEvent
import io.komune.im.f2.user.domain.command.UserUpdatedPasswordEvent
import io.komune.im.f2.user.domain.query.UserExistsByEmailFunction
import io.komune.im.f2.user.domain.query.UserExistsByEmailQuery
import io.komune.im.f2.user.domain.query.UserExistsByEmailResult
import io.komune.im.f2.user.domain.query.UserGetByEmailFunction
import io.komune.im.f2.user.domain.query.UserGetByEmailQuery
import io.komune.im.f2.user.domain.query.UserGetByEmailResult
import io.komune.im.f2.user.domain.query.UserGetFunction
import io.komune.im.f2.user.domain.query.UserGetQuery
import io.komune.im.f2.user.domain.query.UserGetResult
import io.komune.im.f2.user.domain.query.UserPageFunction
import io.komune.im.f2.user.domain.query.UserPageQuery
import io.komune.im.f2.user.domain.query.UserPageResult
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
    override fun userGet(): UserGetFunction = client.function<UserGetQuery, UserGetResult>(this::userGet.name)
    override fun userGetByEmail(): UserGetByEmailFunction = client.function<UserGetByEmailQuery, UserGetByEmailResult>(this::userGetByEmail.name)
    override fun userExistsByEmail(): UserExistsByEmailFunction = client.function<UserExistsByEmailQuery, UserExistsByEmailResult>(this::userExistsByEmail.name)
    override fun userPage(): UserPageFunction = client.function<UserPageQuery, UserPageResult>(this::userPage.name)

    override fun userCreate(): UserCreateFunction = client.function<UserCreateCommand, UserCreatedEvent>(this::userCreate.name)
    override fun userUpdate(): UserUpdateFunction = client.function<UserUpdateCommand, UserUpdatedEvent>(this::userUpdate.name)
    override fun userResetPassword(): UserResetPasswordFunction = client.function<UserResetPasswordCommand, UserResetPasswordEvent>(this::userResetPassword.name)
    override fun userUpdateEmail(): UserUpdateEmailFunction = client.function<UserUpdateEmailCommand, UserUpdatedEmailEvent>(this::userUpdateEmail.name)
    override fun userUpdatePassword(): UserUpdatePasswordFunction = client.function<UserUpdatePasswordCommand, UserUpdatedPasswordEvent>(this::userUpdatePassword.name)
    override fun userDisableMfa(): UserDisableMfaFunction = client.function<UserDisableMfaCommand, UserDisabledMfavent>(this::userDisableMfa.name)

    override fun userDisable(): UserDisableFunction = client.function<UserDisableCommand, UserDisabledEvent>(this::userDisable.name)
    override fun userEnable(): UserEnableFunction = client.function<UserEnableCommand, UserEnabledEvent>(this::userEnable.name)

    override fun userDelete(): UserDeleteFunction = client.function<UserDeleteCommand, UserDeletedEvent>(this::userDelete.name)
}
