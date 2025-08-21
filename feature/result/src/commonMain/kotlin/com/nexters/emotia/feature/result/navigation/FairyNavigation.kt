package com.nexters.emotia.feature.result.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nexters.emotia.feature.result.fairy.FairyScreen
import com.nexters.emotia.feature.result.letter.LetterScreen
import com.nexters.emotia.feature.result.result.ResultScreen

fun NavGraphBuilder.fairyScreen(
    onNavigateToOnBoarding: () -> Unit,
    onNavigateToChatting: () -> Unit,
    onNavigateToLetter: (Int, String, String, Int) -> Unit,
    onNavigateToResult: (String, String) -> Unit,
) {
    composable<FairyRoute.FairyMain> { backStackEntry ->
        val data = backStackEntry.toRoute<FairyRoute.FairyMain>()

        FairyScreen(
            fairyId = data.id,
            fairyName = data.name,
            fairyImage = data.imageUrl,
            onNavigateToOnBoarding = onNavigateToOnBoarding,
            onNavigateToChatting = onNavigateToChatting,
            onNavigateToLetter = { id, name, imageUrl ->
                onNavigateToLetter(id, name, imageUrl, data.chatRoomId)
            }
        )
    }

    composable<FairyRoute.Letter> { backStackEntry ->
        val data = backStackEntry.toRoute<FairyRoute.Letter>()

        LetterScreen(
            fairyId = data.id,
            fairyName = data.name,
            fairyImage = data.imageUrl,
            chatRoomId = data.chatRoomId,
            onNavigateToResult = { fairyName, contents ->
                onNavigateToResult(fairyName, contents)
            },
            onNavigateToChatting = onNavigateToChatting
        )
    }

    composable<FairyRoute.Result> { backStackEntry ->
        val data = backStackEntry.toRoute<FairyRoute.Result>()

        ResultScreen(
            fairyName = data.fairyName,
            contents = data.contents,
            onNavigateToOnBoarding = onNavigateToOnBoarding
        )
    }
}
