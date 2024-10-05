package com.ujizin.catchallenge.core.navigation.utils

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ujizin.catchallenge.core.navigation.destination.BottomDestination

fun NavController.navigateToBottomDestination(
    bottomDestination: BottomDestination
) = navigate(bottomDestination.destination) {
    popUpTo(graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavDestination?.hasRoute(vararg entries: BottomDestination) = this?.hierarchy?.any { hierarchy ->
    entries.any { bottomItem -> hierarchy.hasRoute(bottomItem.destination::class) }
} == true
