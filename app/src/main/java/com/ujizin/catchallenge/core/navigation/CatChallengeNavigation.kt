package com.ujizin.catchallenge.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ujizin.catchallenge.core.navigation.destination.Destination
import com.ujizin.catchallenge.feature.favorites.ui.FavoriteScreen
import com.ujizin.catchallenge.feature.home.ui.HomeScreen

@Composable
fun CatChallengeNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            val currentDestination by navController.currentBackStackEntryAsState()
            CatChallengeBottomNavigation(
                currentDestination = currentDestination?.destination,
                onBottomItemClick = navController::navigateToBottomDestination
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Destination.Home,
        ) {
            composable<Destination.Home> {
                HomeScreen(
                    onNavigateToBreedDetail = { id ->
                        // TODO to be implemented
                    }
                )
            }
            composable<Destination.Favorite> {
                FavoriteScreen()
            }
        }
    }
}