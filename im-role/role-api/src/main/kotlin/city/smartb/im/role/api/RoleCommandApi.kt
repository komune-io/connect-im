package city.smartb.im.role.api

import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.role.api.service.RoleAggregateService
import city.smartb.im.role.domain.RoleCommandFeatures
import city.smartb.im.role.domain.features.command.RoleAddCompositesFunction
import city.smartb.im.role.domain.features.command.RoleCreateFunction
import city.smartb.im.role.domain.features.command.RoleUpdateFunction
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.security.RolesAllowed

/**
 * @d2 service
 */
@Configuration
class RoleCommandApi(
    private val roleAggregateService: RoleAggregateService
): RoleCommandFeatures {
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
