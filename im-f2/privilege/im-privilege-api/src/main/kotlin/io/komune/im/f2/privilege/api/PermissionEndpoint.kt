package io.komune.im.f2.privilege.api

import io.komune.im.commons.auth.policies.f2Function
import io.komune.im.f2.privilege.api.service.PrivilegePoliciesEnforcer
import io.komune.im.f2.privilege.domain.PermissionApi
import io.komune.im.f2.privilege.domain.permission.command.PermissionDefineFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionGetResult
import io.komune.im.f2.privilege.domain.permission.query.PermissionListFunction
import io.komune.im.f2.privilege.domain.permission.query.PermissionListResult
import io.komune.im.f2.privilege.lib.PrivilegeAggregateService
import io.komune.im.f2.privilege.lib.PrivilegeFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class PermissionEndpoint(
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val privilegePoliciesEnforcer: PrivilegePoliciesEnforcer
): PermissionApi {
    private val logger by Logger()

    @Bean
    override fun permissionGet(): PermissionGetFunction = f2Function { query ->
        logger.info("permissionGet: $query")
        privilegePoliciesEnforcer.checkGet()
        privilegeFinderService.getPermissionOrNull(query.identifier)
            .let(::PermissionGetResult)
    }

    @Bean
    override fun permissionList(): PermissionListFunction = f2Function { query ->
        logger.info("permissionList: $query")
        privilegePoliciesEnforcer.checkList()
        privilegeFinderService.listPermissions().let(::PermissionListResult)
    }

    @Bean
    override fun permissionDefine(): PermissionDefineFunction = f2Function { command ->
        logger.info("permissionDefine: $command")
        privilegePoliciesEnforcer.checkDefine()
        privilegeAggregateService.define(command)
    }
}
