package city.smartb.im.organization.api.config

import city.smartb.im.organization.api.OrganizationMapperImpl
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.OrganizationFeaturesImpl
import city.smartb.im.organization.lib.service.OrganizationAggregateService
import city.smartb.im.organization.lib.service.OrganizationFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationFeaturesConfig {

    @Bean
    fun organizationFeatures(
        organizationFinderService: OrganizationFinderService<Organization>,
        organizationAggregateService: OrganizationAggregateService<Organization>,
    ) = OrganizationFeaturesImpl(
        organizationFinderService,
        organizationAggregateService,
        OrganizationMapperImpl()
    )
}
