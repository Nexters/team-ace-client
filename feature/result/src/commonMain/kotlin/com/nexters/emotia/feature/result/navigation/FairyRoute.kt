package com.nexters.emotia.feature.result.navigation

import com.nexters.emotia.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface FairyRoute : Route {
    @Serializable
    data class FairyMain(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val silhouetteImageUrl: String,
        val chatRoomId: Int,
) : FairyRoute

    @Serializable
    data class Letter(val id: Int, val name: String, val imageUrl: String, val chatRoomId: Int) : FairyRoute

    @Serializable
    data class Result(val fairyId: Int, val fairyName: String, val fairyImage: String, val contents: String) : FairyRoute
}
