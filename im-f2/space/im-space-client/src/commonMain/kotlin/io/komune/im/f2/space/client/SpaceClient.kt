package io.komune.im.f2.space.client

import f2.client.F2Client
import f2.client.function
import f2.client.ktor.http.plugin.model.AuthRealm
import f2.dsl.fnc.F2SupplierSingle
import io.komune.im.f2.space.domain.SpaceApi
import io.komune.im.f2.space.domain.command.SpaceDefineFunction
import io.komune.im.f2.space.domain.command.SpaceDeleteFunction
import io.komune.im.f2.space.domain.query.SpaceGetFunction
import io.komune.im.f2.space.domain.query.SpacePageFunction
import kotlin.js.JsExport

expect fun F2Client.spaceClient(): F2SupplierSingle<SpaceClient>
expect fun spaceClient(urlBase: String, getAuth: suspend () -> AuthRealm): F2SupplierSingle<SpaceClient>

@JsExport
open class SpaceClient constructor(private val client: F2Client): SpaceApi {
    override fun spaceDefine(): SpaceDefineFunction = client.function(this::spaceDefine.name)
    override fun spaceDelete(): SpaceDeleteFunction = client.function(this::spaceDelete.name)
    override fun spaceGet(): SpaceGetFunction = client.function(this::spaceGet.name)
    override fun spacePage(): SpacePageFunction = client.function(this::spacePage.name)
}
