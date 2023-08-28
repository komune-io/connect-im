package city.smartb.im.space.api

import city.smartb.im.commons.auth.policies.verify
import city.smartb.im.space.api.policies.SpacePoliciesEnforcer
import city.smartb.im.space.domain.features.command.SpaceCreateFunction
import city.smartb.im.space.domain.features.command.SpaceDeleteFunction
import city.smartb.im.space.domain.features.query.SpaceGetFunction
import city.smartb.im.space.domain.features.query.SpacePageFunction
import city.smartb.im.space.lib.SpaceFeaturesImpl
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

/**
 * @d2 service
 * @parent [city.smartb.im.space.domain.D2SpacePage]
 */
@Service
class SpaceEndpoint(
    private val spaceFeatures: SpaceFeaturesImpl,
    private val spacePoliciesEnforcer: SpacePoliciesEnforcer,
) {

    /**
     * Fetch an Space by its ID.
     */
    @Bean
    fun spaceGet(): SpaceGetFunction = verify(spaceFeatures.spaceGet()) { query ->
        spacePoliciesEnforcer.checkGet(query.id)
    }

    /**
     * Fetch a page of spaces.
     */
    @Bean
    fun spacePage(): SpacePageFunction = verify(spaceFeatures.spacePage()) { query ->
        spacePoliciesEnforcer.checkPage()
    }

    /**
     * Create an space.
     */
    @Bean
    fun spaceCreate(): SpaceCreateFunction = verify(spaceFeatures.spaceCreate()) { command ->
        spacePoliciesEnforcer.checkCreate()
    }

    /**
     * Delete an space and its users.
     */
    @Bean
    fun spaceDelete(): SpaceDeleteFunction = verify(spaceFeatures.spaceDelete()) { command ->
        spacePoliciesEnforcer.checkDelete(command.id)
    }

}
