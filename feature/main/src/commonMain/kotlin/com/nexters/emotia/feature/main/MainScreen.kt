package com.nexters.emotia.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nexters.emotia.core.navigation.navigateTo
import com.nexters.emotia.core.navigation.navigateToAndClearBackStack
import com.nexters.emotia.feature.chatting.navigation.ChattingRoute
import com.nexters.emotia.feature.chatting.navigation.chattingScreen
import com.nexters.emotia.feature.onboarding.navigation.OnBoardingRoute
import com.nexters.emotia.feature.onboarding.navigation.onBoardingScreen
import com.nexters.emotia.feature.result.navigation.ResultRoute
import com.nexters.emotia.feature.result.navigation.resultScreen

@Composable
expect fun MainScreen(modifier: Modifier = Modifier)

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OnBoardingRoute.OnBoardingMain,
        modifier = modifier
    ) {
        onBoardingScreen(
            onNavigateNext = {
                navController.navigateTo(ChattingRoute.ChattingMain)
            }
        )

        chattingScreen(
            onNavigateToResult = {
                navController.navigateTo(ResultRoute.ResultMain)
            }
        )

        resultScreen(
            onNavigateToOnBoarding = {
                navController.navigateToAndClearBackStack(OnBoardingRoute.OnBoardingMain)
            },
            onNavigateToChatting = {
                navController.navigateTo(ChattingRoute.ChattingMain)
            }
        )
    }
}
