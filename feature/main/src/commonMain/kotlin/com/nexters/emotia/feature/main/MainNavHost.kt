package com.nexters.emotia.feature.main

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.nexters.emotia.core.designsystem.theme.EmotiaTheme.colors
import com.nexters.emotia.feature.chatting.navigation.chattingScreen
import com.nexters.emotia.feature.onboarding.navigation.onBoardingScreen
import com.nexters.emotia.feature.result.navigation.fairyScreen

@Composable
internal fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
    deviceUuid: String,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier.background(color = colors.black)
    ) {
        onBoardingScreen(
            onNavigateNext = {
                navigator.navigateToChatting()
            },
            deviceUuid = deviceUuid
        )

        chattingScreen(
            onNavigateToFairy = { id, name, image, silhouetteImage, chatRoomId ->
                navigator.navigateToFairy(id, name, image, silhouetteImage, chatRoomId)
            }
        )

        fairyScreen(
            onNavigateToOnBoarding = {
                navigator.navigateToOnBoarding()
            },
            onNavigateToChatting = {
                navigator.navigateToChatting()
            },
            onNavigateToLetter = { id, name, image, chatRoomId ->
                navigator.navigateToLetter(id, name, image, chatRoomId)
            },
            onNavigateToResult = { fairyName, contents ->
                navigator.navigateToLetterResult(fairyName, contents)
            }
        )
    }
}
