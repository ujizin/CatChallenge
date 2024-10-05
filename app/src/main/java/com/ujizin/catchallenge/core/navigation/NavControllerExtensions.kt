package com.ujizin.catchallenge.core.navigation

import androidx.navigation.NavController
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
