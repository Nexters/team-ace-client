package com.nexters.emotia.feature.chatting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexters.emotia.feature.chatting.ChattingScreen

fun NavGraphBuilder.chattingScreen(
    onNavigateToFairy: (Int, String, String, String, Int) -> Unit,
) {
    composable<ChattingRoute.ChattingMain> {
        ChattingScreen(
            onNavigateToFairy = { id, name, image, silhouetteImage, chatRoomId ->
                onNavigateToFairy(id, name, image, silhouetteImage, chatRoomId)
            }
        )
    }
}
