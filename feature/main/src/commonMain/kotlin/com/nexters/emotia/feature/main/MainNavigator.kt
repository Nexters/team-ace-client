package com.nexters.emotia.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexters.emotia.feature.chatting.navigation.ChattingRoute
import com.nexters.emotia.feature.main.util.navigateTo
import com.nexters.emotia.feature.main.util.navigateToAndClearBackStack
import com.nexters.emotia.feature.onboarding.navigation.OnBoardingRoute
import com.nexters.emotia.feature.result.navigation.FairyRoute

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = OnBoardingRoute.OnBoardingMain

    fun navigateToOnBoarding() {
        navController.navigateToAndClearBackStack(OnBoardingRoute.OnBoardingMain)
    }

    fun navigateToChatting() {
        navController.navigateTo(ChattingRoute.ChattingMain)
    }

    fun navigateToFairy(
        id: Int,
        name: String,
        image: String,
        silhouetteImage: String,
        chatRoomId: Int,
    ) {
        navController.navigateTo(FairyRoute.FairyMain(id, name, image, silhouetteImage, chatRoomId))
    }

    fun navigateToLetter(
        id: Int,
        name: String,
        image: String,
        chatRoomId: Int,
    ) {
        navController.navigateTo(FairyRoute.Letter(id, name, image, chatRoomId))
    }

    fun navigateToLetterResult(
        fairyName: String,
        contents: String,
    ) {
        navController.navigateTo(FairyRoute.Result(fairyName, contents))
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackIfNotOnBoarding() {
        if (navController.currentBackStackEntry != null) {
            popBackStack()
        }
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
