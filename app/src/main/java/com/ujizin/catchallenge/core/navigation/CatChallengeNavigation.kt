package com.ujizin.catchallenge.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ujizin.catchallenge.core.navigation.destination.Destination
import com.ujizin.catchallenge.feature.favorites.ui.FavoriteScreen
import com.ujizin.catchallenge.feature.home.ui.HomeScreen

@Composable
fun CatChallengeNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.Home,
    ) {
        composable<Destination.Home> {
            HomeScreen()
        }
        composable<Destination.Favorite> {
            FavoriteScreen()
        }
    }
}