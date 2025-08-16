package com.nexters.emotia.feature.result.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nexters.emotia.feature.result.ResultScreen

fun NavGraphBuilder.resultScreen(
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
) {
    composable<ResultRoute.ResultMain> { backStackEntry ->
        val data = backStackEntry.toRoute<ResultRoute.ResultMain>()

        ResultScreen(
            fairyId = data.id,
            fairyName = data.name,
            fairyImage = data.imageUrl,
            onNavigateToOnBoarding = onNavigateToOnBoarding,
            onNavigateToChatting = onNavigateToChatting
        )
    }
}
