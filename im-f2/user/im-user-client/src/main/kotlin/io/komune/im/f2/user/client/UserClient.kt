package io.komune.im.f2.user.client

import io.komune.im.commons.http.ClientBuilder
import io.komune.im.commons.http.ClientJvm
import io.komune.im.commons.http.HttpClientBuilderJvm
import io.komune.im.f2.user.domain.command.UserCreateCommand
import io.komune.im.f2.user.domain.command.UserCreatedEvent
import io.komune.im.f2.user.domain.command.UserUpdateCommand
import io.komune.im.f2.user.domain.command.UserUpdatePasswordCommand
import io.komune.im.f2.user.domain.command.UserUpdatedEvent
import io.komune.im.f2.user.domain.command.UserUpdatedPasswordEvent
import io.komune.im.f2.user.domain.query.UserGetQuery
import io.komune.im.f2.user.domain.query.UserGetResult
import io.komune.im.f2.user.domain.query.UserPageQuery
import io.komune.im.f2.user.domain.query.UserPageResult

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
