package com.nexters.emotia.feature.onboarding.navigation

import com.nexters.emotia.core.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface OnBoardingRoute : Route {
    @Serializable
    data object OnBoardingMain : OnBoardingRoute
}
