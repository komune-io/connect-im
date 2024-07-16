package io.komune.im.script.core.model

import io.komune.im.f2.privilege.domain.feature.command.FeatureDefineCommand

data class FeatureData(
    val name: String,
    val description: String,
) {
    fun toCommand() = FeatureDefineCommand(
        identifier = name,
        description = description
    )
}
