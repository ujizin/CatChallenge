package com.ujizin.catchallenge.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import com.ujizin.catchallenge.core.navigation.destination.BottomDestination
import com.ujizin.catchallenge.core.navigation.utils.hasRoute
import com.ujizin.catchallenge.core.ui.theme.CatChallengeThemeForPreview

@Composable
fun CatChallengeBottomNavigation(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onBottomItemClick: (BottomDestination) -> Unit,
) {
    val bottomDestinations = remember { BottomDestination.entries }
    val isBottomDestination = remember(currentDestination) {
        currentDestination.hasRoute(*bottomDestinations.toTypedArray())
    }
    AnimatedVisibility(
        visible = isBottomDestination,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 }
    ) {
        NavigationBar(modifier = modifier) {
            bottomDestinations.forEach { bottomItem ->
                val isSelected = remember(currentDestination) {
                    currentDestination.hasRoute(bottomItem)
                }
                NavigationItem(
                    bottomItem = bottomItem,
                    isSelected = isSelected,
                    onBottomItemClick = onBottomItemClick,
                )
            }
        }
    }
}

@Composable
private fun RowScope.NavigationItem(
    bottomItem: BottomDestination,
    isSelected: Boolean,
    onBottomItemClick: (BottomDestination) -> Unit
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
        onClick = { onBottomItemClick(bottomItem) }
    )
}

@Preview
@Composable
private fun CatChallengeBottomNavigationPreview() {
    CatChallengeThemeForPreview {
        CatChallengeBottomNavigation(
            modifier = Modifier,
            currentDestination = null,
            onBottomItemClick = { },
        )
    }
}
