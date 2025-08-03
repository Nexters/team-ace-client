package com.nexters.emotia.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.emotia.feature.onboarding.OnBoardingScreen

fun NavGraphBuilder.onBoardingScreen(
    onNavigateNext: () -> Unit,
) {
    composable<OnBoardingRoute.OnBoardingMain> {
        OnBoardingScreen(
            onNavigateToChatting = onNavigateNext
        )
    }
}
