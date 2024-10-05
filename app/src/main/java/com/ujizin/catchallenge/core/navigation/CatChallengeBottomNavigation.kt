package com.ujizin.catchallenge.core.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ujizin.catchallenge.core.navigation.destination.BottomDestination
import com.ujizin.catchallenge.core.navigation.destination.Destination

@Composable
fun CatChallengeBottomNavigation(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onBottomItemClick: (Destination) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        val bottomDestinations = remember { BottomDestination.entries }
        bottomDestinations.forEach { bottomItem ->
            val isSelected = remember(currentDestination) {
                currentDestination?.hierarchy?.any {
                    it.hasRoute(bottomItem.destination::class)
                } == true
            }
            NavigationItem(
                bottomItem = bottomItem,
                isSelected = isSelected,
                onBottomItemClick = onBottomItemClick,
            )
        }
    }
}

@Composable
private fun RowScope.NavigationItem(
    bottomItem: BottomDestination,
    isSelected: Boolean,
    onBottomItemClick: (Destination) -> Unit
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(bottomItem.labelRes))
        },
        icon = {
            Icon(
                imageVector = bottomItem.iconVector,
                contentDescription = stringResource(bottomItem.labelRes)
            )
        },
        alwaysShowLabel = isSelected,
        selected = isSelected,
        onClick = { onBottomItemClick(bottomItem.destination) }
    )
}