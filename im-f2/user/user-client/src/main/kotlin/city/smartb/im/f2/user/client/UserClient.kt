package city.smartb.im.f2.user.client

import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import city.smartb.im.f2.user.domain.command.UserCreateCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserCreatedEventDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdateCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatePasswordCommandDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatedEventDTOBase
import city.smartb.im.f2.user.domain.command.UserUpdatedPasswordEventDTOBase
import city.smartb.im.f2.user.domain.query.UserGetQuery
import city.smartb.im.f2.user.domain.query.UserGetResult
import city.smartb.im.f2.user.domain.query.UserPageQuery
import city.smartb.im.f2.user.domain.query.UserPageResult

class UserClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken) {

    suspend fun userGet(queries: List<UserGetQuery>):
            List<UserGetResult> = post("userGet", queries)

    suspend fun userPage(queries: List<UserPageQuery>):
            List<UserPageResult> = post("userPage", queries)

    suspend fun userCreate(commands: List<UserCreateCommandDTOBase>):
            List<UserCreatedEventDTOBase> = post("userCreate", commands)

    suspend fun userUpdate(commands: List<UserUpdateCommandDTOBase>):
            List<UserUpdatedEventDTOBase> = post("userUpdate", commands)

    suspend fun userResetPassword(commands: List<UserUpdatePasswordCommandDTOBase>):
            List<UserUpdatedPasswordEventDTOBase> = post("userResetPassword", commands)
}
