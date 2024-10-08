package com.ujizin.catchallenge.core.navigation.destination

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.ujizin.catchallenge.core.ui.R

enum class BottomDestination(
    @StringRes
    val labelRes: Int,
    val destination: Destination,
    val iconVector: ImageVector,
) {
    Home(
        labelRes = R.string.home_label,
        destination = Destination.Home,
        iconVector = Icons.Filled.Home,
    ),
    Favorite(
        labelRes = R.string.favorite_label,
        destination = Destination.Favorite,
        iconVector = Icons.Filled.Favorite,
    ),
}