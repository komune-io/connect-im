package city.smartb.im.user.client

import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.user.domain.features.command.UserCreateCommand
import city.smartb.im.user.domain.features.command.UserCreateResult
import city.smartb.im.user.domain.features.command.UserResetPasswordCommand
import city.smartb.im.user.domain.features.command.UserResetPasswordResult
import city.smartb.im.user.domain.features.command.UserUpdateCommand
import city.smartb.im.user.domain.features.command.UserUpdateResult
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.features.query.UserGetResult
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.domain.features.query.UserPageResult

class UserClient(
    url: String,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, generateBearerToken) {

    suspend fun userGet(queries: List<UserGetQuery>):
            List<UserGetResult> = post("userGet", queries)

    suspend fun userPage(queries: List<UserPageQuery>):
            List<UserPageResult> = post("userPage", queries)

    suspend fun userCreate(commands: List<UserCreateCommand>):
            List<UserCreateResult> = post("userCreate", commands)

    suspend fun userUpdate(commands: List<UserUpdateCommand>):
            List<UserUpdateResult> = post("userUpdate", commands)

    suspend fun userResetPassword(commands: List<UserResetPasswordCommand>):
            List<UserResetPasswordResult> = post("userResetPassword", commands)
}
