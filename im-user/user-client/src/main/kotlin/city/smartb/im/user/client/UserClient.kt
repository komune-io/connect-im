package city.smartb.im.user.client

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
import city.smartb.im.commons.http.ClientJvm

class UserClient(
    url: String
): ClientJvm(url) {

    suspend fun userGet(command: List<UserGetQuery>):
            List<UserGetResult> = post("userGet", command)

    suspend fun userPage(command: List<UserPageQuery>):
            List<UserPageResult> = post("userPage", command)

    suspend fun userCreate(command: List<UserCreateCommand>):
            List<UserCreateResult> = post("userCreate", command)

    suspend fun userUpdate(command: List<UserUpdateCommand>):
            List<UserUpdateResult> = post("userUpdate", command)

    suspend fun userResetPassword(command: List<UserResetPasswordCommand>):
            List<UserResetPasswordResult> = post("userResetPassword", command)
}
