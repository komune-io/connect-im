package city.smartb.im.privilege.api

import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.privilege.api.service.PrivilegeAggregateService
import city.smartb.im.privilege.domain.RoleCommandApi
import city.smartb.im.privilege.domain.role.command.RoleAddCompositesFunction
import city.smartb.im.privilege.domain.role.command.RoleCreateFunction
import city.smartb.im.privilege.domain.role.command.RoleUpdateFunction
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.security.RolesAllowed

/**
 * @d2 service
 */
@Configuration
class RoleCommandApi(
    private val privilegeAggregateService: PrivilegeAggregateService
): RoleCommandApi {
    /**
     * Associate roles to another role. Associated roles must exist.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    override fun roleAddComposites(): RoleAddCompositesFunction = f2Function { cmd ->
        roleAggregateService.roleAddComposites(cmd)
    }

    /**
     * Create a Role.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    override fun roleCreate(): RoleCreateFunction = f2Function { cmd ->
        roleAggregateService.roleCreate(cmd)
    }

    /**
     * Update a Role.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    override fun roleUpdate(): RoleUpdateFunction = f2Function { cmd ->
        roleAggregateService.roleUpdate(cmd)
    }
}
