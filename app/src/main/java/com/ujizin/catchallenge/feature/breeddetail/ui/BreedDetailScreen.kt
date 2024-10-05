package com.ujizin.catchallenge.feature.breeddetail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import com.ujizin.catchallenge.feature.breeddetail.BreedDetailViewModel

@Composable
fun BreedDetailScreen(
    onBackPressed: () -> Unit,
    viewModel: BreedDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BreedDetailContent()
}

@Composable
fun BreedDetailContent(modifier: Modifier = Modifier) {
    // Todo to be implemented
}

@Preview
@Composable
private fun BreedDetailContentPreview() {
    CatChallengeTheme {
        BreedDetailContent()
    }
}