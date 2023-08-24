package city.smartb.im.privilege.api

import city.smartb.im.commons.auth.Roles
import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.privilege.domain.PermissionApi
import city.smartb.im.privilege.domain.permission.command.PermissionDefineFunction
import city.smartb.im.privilege.domain.permission.query.PermissionGetFunction
import city.smartb.im.privilege.domain.permission.query.PermissionGetResult
import city.smartb.im.privilege.lib.PrivilegeAggregateService
import city.smartb.im.privilege.lib.PrivilegeFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.annotation.security.RolesAllowed

@Configuration
class PermissionEndpoint(
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService
): PermissionApi {
    private val logger by Logger()

    @Bean
    @RolesAllowed(Roles.IM_ROLE_WRITE)
    override fun permissionDefine(): PermissionDefineFunction = f2Function { command ->
        logger.info("permissionDefine: $command")
        privilegeAggregateService.define(command)
    }

    @Bean
    @RolesAllowed(Roles.IM_ROLE_READ)
    override fun permissionGet(): PermissionGetFunction = f2Function { query ->
        logger.info("permissionGet: $query")
        privilegeFinderService.getPermissionOrNull(query.realmId, query.identifier)
            .let(::PermissionGetResult)
    }
}
