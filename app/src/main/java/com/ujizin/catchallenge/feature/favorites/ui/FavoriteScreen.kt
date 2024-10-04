package com.ujizin.catchallenge.feature.favorites.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import com.ujizin.catchallenge.feature.favorites.FavoriteHomeViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteHomeViewModel = hiltViewModel(),
) {

    FavoriteContent(
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun FavoriteContent(modifier: Modifier = Modifier) {
    // TODO to be implemented
}

@Preview
@Composable
private fun FavoriteContentPreview() {
    CatChallengeTheme {
        FavoriteContent()
    }
}