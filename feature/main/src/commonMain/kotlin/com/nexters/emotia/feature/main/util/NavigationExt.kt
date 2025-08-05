package com.nexters.emotia.feature.main.util

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.nexters.emotia.core.navigation.Route

fun NavController.navigateTo(
    route: Route,
    navOptions: NavOptions? = null,
) {
    navigate(route, navOptions)
}

fun NavController.navigateToAndClearBackStack(route: Route) {
    navigate(route) {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavController.navigateToAndReplace(route: Route) {
    navigate(route) {
        popUpTo(currentBackStackEntry?.destination?.route ?: return@navigate) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
