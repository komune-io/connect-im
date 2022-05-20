package city.smartb.im.role.api

import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.role.api.service.RoleAggregateService
import city.smartb.im.role.domain.features.command.RoleAddCompositesFunction
import city.smartb.im.role.domain.features.command.RoleCreateFunction
import city.smartb.im.role.domain.features.command.RoleUpdateFunction
import f2.dsl.fnc.f2Function
import javax.annotation.security.RolesAllowed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @d2 service
 * @title Role/Entrypoints
 */
@Configuration
class RoleEndpoint(
    private val roleAggregateService: RoleAggregateService
) {
    /**
     * Associates roles to another role. Associated roles must exist.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleAddComposites(): RoleAddCompositesFunction = f2Function { cmd ->
        roleAggregateService.roleAddComposites(cmd)
    }

    /**
     * Creates a Role.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleCreate(): RoleCreateFunction = f2Function { cmd ->
        roleAggregateService.roleCreate(cmd)
    }

    /**
     * Updates a Role.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    fun roleUpdate(): RoleUpdateFunction = f2Function { cmd ->
        roleAggregateService.roleUpdate(cmd)
    }
}
