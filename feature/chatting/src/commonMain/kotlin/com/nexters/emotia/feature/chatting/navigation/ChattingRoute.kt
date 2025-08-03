package com.nexters.emotia.feature.chatting.navigation

import com.nexters.emotia.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface ChattingRoute : Route {
    @Serializable
    data object ChattingMain : ChattingRoute
}

