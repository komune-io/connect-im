package city.smartb.im.organization.api.config

import city.smartb.im.organization.api.OrganizationMapperImpl
import city.smartb.im.organization.api.service.OrganizationAggregateServiceDefault
import city.smartb.im.organization.api.service.OrganizationFinderServiceDefault
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.OrganizationFeaturesImpl
import city.smartb.im.organization.lib.service.OrganizationMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationFeaturesConfig {

    @Bean
    fun  organizationMapper(): OrganizationMapper<Organization, Organization> =  OrganizationMapperImpl()
    @Bean
    fun organizationFeatures(
        organizationFinderService: OrganizationFinderServiceDefault,
        organizationAggregateService: OrganizationAggregateServiceDefault,
        organizationMapper: OrganizationMapper<Organization, Organization>,
    ) = OrganizationFeaturesImpl(
        organizationFinderService,
        organizationAggregateService,
        organizationMapper
    )
}
