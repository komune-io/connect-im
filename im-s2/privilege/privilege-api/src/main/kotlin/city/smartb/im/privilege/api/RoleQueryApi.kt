package city.smartb.im.privilege.api

import city.smartb.i2.spring.boot.auth.SUPER_ADMIN_ROLE
import city.smartb.im.privilege.api.service.PrivilegeFinderService
import city.smartb.im.privilege.domain.RoleQueryApi
import city.smartb.im.privilege.domain.role.query.RoleGetByIdFunction
import city.smartb.im.privilege.domain.role.query.RoleGetByNameFunction
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.security.RolesAllowed

/**
 * @d2 query service
 */
@Configuration
class RoleQueryApi(
    private val roleQueryService: RoleFinderService
): RoleQueryFeatures {
    /**
     * Fetch a role by its id.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    override fun roleGetById(): RoleGetByIdFunction = f2Function { query ->
        roleQueryService.getById(query)
    }

    /**
     * Fetch a role by its name.
     */
    @Bean
    @RolesAllowed(SUPER_ADMIN_ROLE)
    override fun roleGetByName(): RoleGetByNameFunction = f2Function { query ->
        roleQueryService.getByName(query)
    }

}
