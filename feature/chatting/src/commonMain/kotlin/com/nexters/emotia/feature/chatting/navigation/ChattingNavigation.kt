package com.nexters.emotia.feature.chatting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.emotia.feature.chatting.ChattingScreen

fun NavGraphBuilder.chattingScreen(
    onNavigateToResult: (Int, String, String) -> Unit,
) {
    composable<ChattingRoute.ChattingMain> {
        ChattingScreen(
            onNavigateToResult = { id, name, image ->
                onNavigateToResult(id, name, image)
            }
        )
    }
}
