package com.nexters.emotia.feature.result.navigation

import com.nexters.emotia.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface ResultRoute : Route {
    @Serializable
    data class ResultMain(val id: Int, val name: String, val imageUrl: String) : ResultRoute
}
