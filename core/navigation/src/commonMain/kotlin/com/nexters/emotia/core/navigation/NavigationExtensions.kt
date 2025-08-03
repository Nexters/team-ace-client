package com.nexters.emotia.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateTo(
    route: Route,
    navOptions: NavOptions? = null,
) {
    navigate(route, navOptions)
}

fun NavController.navigateToAndClearBackStack(route: Route) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavController.navigateToAndReplace(route: Route) {
    navigate(route) {
        popUpTo(currentDestination?.id ?: return@navigate) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
