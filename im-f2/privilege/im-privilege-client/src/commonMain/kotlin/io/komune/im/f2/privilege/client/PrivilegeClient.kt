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
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineCommand
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineFunction
import io.komune.im.f2.privilege.domain.feature.command.FeatureDefinedEvent
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetQuery
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetResult
import io.komune.im.f2.privilege.domain.feature.query.FeatureListFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureListQuery
import io.komune.im.f2.privilege.domain.feature.query.FeatureListResult
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineCommand
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineFunction
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefinedEvent
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetQuery
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetResult
import io.komune.im.f2.privilege.domain.permission.query.PermissionListFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionListQuery
import io.komune.im.f2.privilege.domain.permission.query.PermissionListResult
import io.komune.im.f2.privilege.domain.role.command.RoleDefineCommand
import io.komune.im.f2.privilege.domain.role.command.RoleDefineFunction
import io.komune.im.f2.privilege.domain.role.command.RoleDefinedEvent
import io.komune.im.f2.privilege.domain.role.query.RoleGetFunction
import io.komune.im.f2.privilege.domain.role.query.RoleGetQuery
import io.komune.im.f2.privilege.domain.role.query.RoleGetResult
import io.komune.im.f2.privilege.domain.role.query.RoleListFunction
import io.komune.im.f2.privilege.domain.role.query.RoleListQuery
import io.komune.im.f2.privilege.domain.role.query.RoleListResult
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
    override fun featureDefine(): FeatureDefineFunction = client.function<FeatureDefineCommand, FeatureDefinedEvent>(this::featureDefine.name)
    override fun featureGet(): FeatureGetFunction = client.function<FeatureGetQuery, FeatureGetResult>(this::featureGet.name)
    override fun featureList(): FeatureListFunction = client.function<FeatureListQuery, FeatureListResult>(this::featureList.name)

    override fun permissionDefine(): PermissionDefineFunction = client.function<PermissionDefineCommand, PermissionDefinedEvent>(this::permissionDefine.name)
    override fun permissionGet(): PermissionGetFunction = client.function<PermissionGetQuery, PermissionGetResult>(this::permissionGet.name)
    override fun permissionList(): PermissionListFunction = client.function<PermissionListQuery, PermissionListResult>(this::permissionList.name)

    override fun roleDefine(): RoleDefineFunction = client.function<RoleDefineCommand, RoleDefinedEvent>(this::roleDefine.name)
    override fun roleGet(): RoleGetFunction = client.function<RoleGetQuery, RoleGetResult>(this::roleGet.name)
    override fun roleList(): RoleListFunction = client.function<RoleListQuery, RoleListResult>(this::roleList.name)
}
