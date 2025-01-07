package io.komune.im.f2.privilege.client

import f2.client.F2Client
import f2.client.domain.AuthRealm
import f2.client.function
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.http.plugin.F2Auth
import f2.dsl.fnc.F2SupplierSingle
import f2.dsl.fnc.f2SupplierSingle
import io.komune.im.f2.privilege.domain.FeatureApi
import io.komune.im.f2.privilege.domain.PermissionApi
import io.komune.im.f2.privilege.domain.RoleApi
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureListFunction
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionListFunction
import io.komune.im.f2.privilege.domain.role.command.RoleDefineFunction
import io.komune.im.f2.privilege.domain.role.query.RoleGetFunction
import io.komune.im.f2.privilege.domain.role.query.RoleListFunction
import io.ktor.client.plugins.HttpTimeout
import kotlin.js.JsExport

fun F2Client.privilegeClient(): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(this)
}

fun privilegeClient(
    urlBase: String,
    getAuth: suspend () -> AuthRealm,
): F2SupplierSingle<PrivilegeClient> = f2SupplierSingle {
    PrivilegeClient(
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
open class PrivilegeClient(private val client: F2Client): FeatureApi, RoleApi, PermissionApi {
    override fun featureDefine(): FeatureDefineFunction = client.function(this::featureDefine.name)
    override fun featureGet(): FeatureGetFunction = client.function(this::featureGet.name)
    override fun featureList(): FeatureListFunction = client.function(this::featureList.name)

    override fun permissionDefine(): PermissionDefineFunction = client.function(this::permissionDefine.name)
    override fun permissionGet(): PermissionGetFunction = client.function(this::permissionGet.name)
    override fun permissionList(): PermissionListFunction = client.function(this::permissionList.name)

    override fun roleDefine(): RoleDefineFunction = client.function(this::roleDefine.name)
    override fun roleGet(): RoleGetFunction = client.function(this::roleGet.name)
    override fun roleList(): RoleListFunction = client.function(this::roleList.name)
}
