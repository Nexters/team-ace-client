package com.nexters.emotia.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.nexters.emotia.feature.chatting.navigation.chattingScreen
import com.nexters.emotia.feature.onboarding.navigation.onBoardingScreen
import com.nexters.emotia.feature.result.navigation.resultScreen

@Composable
internal fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
    deviceUuid: String,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        onBoardingScreen(
            onNavigateNext = {
                navigator.navigateToChatting()
            },
            deviceUuid = deviceUuid
        )

        chattingScreen(
            onNavigateToResult = {
                navigator.navigateToResult()
            }
        )

        resultScreen(
            onNavigateToOnBoarding = {
                navigator.navigateToOnBoarding()
            },
            onNavigateToChatting = {
                navigator.navigateToChatting()
            }
        )
    }
}