package io.komune.im.f2.privilege.domain

import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureGetFunction
import io.komune.im.f2.privilege.domain.feature.query.FeatureListFunction

/**
 * @d2 api
 * @parent [D2FeaturePage]
 */
interface FeatureApi: FeatureCommandApi, FeatureQueryApi

interface FeatureCommandApi {
    /** Create or update a feature */
    fun featureDefine(): FeatureDefineFunction
}

interface FeatureQueryApi {
    /** Get a feature by identifier */
    fun featureGet(): FeatureGetFunction
    /** Get a list of features */
    fun featureList(): FeatureListFunction
}
