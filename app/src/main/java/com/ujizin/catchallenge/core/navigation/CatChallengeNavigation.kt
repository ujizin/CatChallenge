package com.ujizin.catchallenge.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
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
import com.ujizin.catchallenge.core.navigation.utils.navigateToBottomDestination
import com.ujizin.catchallenge.feature.breeddetail.ui.BreedDetailScreen
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
            composable<Destination.Home>(
                exitTransition = { ExitTransition.None },
                enterTransition = { EnterTransition.None }
            ) {
                HomeScreen(
                    onNavigateToBreedDetail = { breed ->
                        navController.navigate(Destination.BreedDetail(breed))
                    }
                )
            }
            composable<Destination.Favorite>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                FavoriteScreen()
            }
            composable<Destination.BreedDetail>(
                enterTransition = { EnterTransition.None },
                typeMap = Destination.BreedDetail.typeMap
            ) {
                BreedDetailScreen(
                    onBackPressed = navController::navigateUp
                )
            }
        }
    }
}