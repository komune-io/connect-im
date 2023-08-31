package city.smartb.im.user.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import city.smartb.im.user.domain.features.command.UserCreateCommand
import city.smartb.im.user.domain.features.command.UserCreatedEvent
import city.smartb.im.user.domain.features.command.UserUpdateCommand
import city.smartb.im.user.domain.features.command.UserUpdatePasswordCommand
import city.smartb.im.user.domain.features.command.UserUpdatedEvent
import city.smartb.im.user.domain.features.command.UserUpdatedPasswordEvent
import city.smartb.im.user.domain.features.query.UserGetQuery
import city.smartb.im.user.domain.features.query.UserGetResult
import city.smartb.im.user.domain.features.query.UserPageQuery
import city.smartb.im.user.domain.features.query.UserPageResult

class UserClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

    suspend fun userGet(queries: List<UserGetQuery>):
            List<UserGetResult> = post("userGet", queries)

    suspend fun userPage(queries: List<UserPageQuery>):
            List<UserPageResult> = post("userPage", queries)

    suspend fun userCreate(commands: List<UserCreateCommand>):
            List<UserCreatedEvent> = post("userCreate", commands)

    suspend fun userUpdate(commands: List<UserUpdateCommand>):
            List<UserUpdatedEvent> = post("userUpdate", commands)

    suspend fun userResetPassword(commands: List<UserUpdatePasswordCommand>):
            List<UserUpdatedPasswordEvent> = post("userResetPassword", commands)
}
