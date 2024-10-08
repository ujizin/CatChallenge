package com.ujizin.catchallenge.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ujizin.catchallenge.core.navigation.CatChallengeBottomNavigation
import com.ujizin.catchallenge.core.navigation.destination.Destination
import com.ujizin.catchallenge.core.navigation.utils.navigateToBottomDestination
import com.ujizin.catchallenge.features.breeddetail.ui.BreedDetailScreen
import com.ujizin.catchallenge.features.favorites.ui.FavoriteScreen
import com.ujizin.catchallenge.features.home.ui.HomeScreen

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
        CatChallengeSharedTransitionLayout(Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Destination.Home,
            ) {
                animatedComposable<Destination.Home> {
                    HomeScreen(
                        onNavigateToBreedDetail = { breed ->
                            navController.navigate(Destination.BreedDetail(breed))
                        }
                    )
                }
                animatedComposable<Destination.Favorite> {
                    FavoriteScreen(
                        onNavigateToBreedDetail = { breed ->
                            navController.navigate(Destination.BreedDetail(breed))
                        }
                    )
                }
                animatedComposable<Destination.BreedDetail>(
                    typeMap = Destination.BreedDetail.typeMap
                ) {
                    BreedDetailScreen(
                        onBackPressed = navController::navigateUp
                    )
                }
            }
        }
    }
}

