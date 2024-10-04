package com.ujizin.catchallenge.feature.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import com.ujizin.catchallenge.feature.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    HomeContent(
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    // TODO to be implemented
}

@Preview
@Composable
private fun HomeContentPreview() {
    CatChallengeTheme {
        HomeContent()
    }
}