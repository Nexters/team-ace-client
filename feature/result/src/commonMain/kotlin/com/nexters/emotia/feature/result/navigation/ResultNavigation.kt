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
        val route = backStackEntry.toRoute<ResultRoute.ResultMain>()
        ResultScreen(
            chatRoomId = route.chatRoomId,
            onNavigateToOnBoarding = onNavigateToOnBoarding,
            onNavigateToChatting = onNavigateToChatting
        )
    }
}
