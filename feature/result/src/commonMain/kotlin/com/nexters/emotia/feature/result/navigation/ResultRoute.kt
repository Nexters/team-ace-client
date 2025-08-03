package com.nexters.emotia.feature.result.navigation

import com.nexters.emotia.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface ResultRoute : Route {
    @Serializable
    data object ResultMain : ResultRoute
}
