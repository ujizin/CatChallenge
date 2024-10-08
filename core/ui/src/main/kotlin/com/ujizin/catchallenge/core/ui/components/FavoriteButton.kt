package com.ujizin.catchallenge.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onFavoriteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = { onFavoriteChanged(!isFavorite) }
    ) {
        val color by animateColorAsState(
            targetValue = if (isFavorite) Color.Red else LocalContentColor.current
        )
        Icon(
            imageVector = when {
                isFavorite -> Icons.Filled.Favorite
                else -> Icons.Outlined.FavoriteBorder
            },
            tint = color,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun FavoriteButtonPreview() {
    CatChallengeTheme {
        var isFavorite by remember { mutableStateOf(false) }
        FavoriteButton(
            isFavorite = isFavorite,
            onFavoriteChanged = { isFavorite = it }
        )
    }
}