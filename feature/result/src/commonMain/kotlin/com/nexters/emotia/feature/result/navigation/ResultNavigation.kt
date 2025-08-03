package com.nexters.emotia.feature.result.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.emotia.feature.result.ResultScreen

fun NavGraphBuilder.resultScreen(
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
) {
    composable<ResultRoute.ResultMain> {
        ResultScreen(
            onNavigateToOnBoarding = onNavigateToOnBoarding,
            onNavigateToChatting = onNavigateToChatting
        )
    }
}
