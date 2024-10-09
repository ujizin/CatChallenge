package com.ujizin.catchallenge.features.favorites.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ujizin.catchallenge.core.ui.R
import com.ujizin.catchallenge.core.ui.components.CenterLoading
import com.ujizin.catchallenge.core.ui.components.EnterAlwaysTopBar
import com.ujizin.catchallenge.core.ui.model.BreedUI
import com.ujizin.catchallenge.core.ui.preview.BreedListParameterProvider
import com.ujizin.catchallenge.core.ui.theme.CatChallengeThemeForPreview
import com.ujizin.catchallenge.features.favorites.FavoriteViewModel
import com.ujizin.catchallenge.features.favorites.ui.components.FavoriteEmptyContainer
import com.ujizin.catchallenge.features.favorites.ui.components.LazyGridListBreed

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigateToBreedDetail: (BreedUI) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteContent(
        modifier = Modifier.fillMaxSize(),
        isLoading = uiState.isLoading,
        favoriteList = uiState.favoriteList,
        onEvent = { event ->
            when (event) {
                is FavoriteUIEvent.OnBreedClick -> onNavigateToBreedDetail(event.breed)
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun FavoriteContent(
    favoriteList: List<BreedUI>,
    isLoading: Boolean,
    onEvent: (FavoriteUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    EnterAlwaysTopBar(
        modifier = modifier,
        expandedHeight = 64.dp,
        header = {
            Text(
                text = stringResource(R.string.favorite_label),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    ) {
        when {
            isLoading -> CenterLoading(Modifier.fillMaxSize())
            favoriteList.isEmpty() -> FavoriteEmptyContainer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )

            else -> LazyGridListBreed(
                modifier = modifier,
                breedList = favoriteList,
                onBreedItemClick = { onEvent(FavoriteUIEvent.OnBreedClick(it)) },
                onFavoriteChanged = { breed, isFavorite ->
                    onEvent(FavoriteUIEvent.OnFavoriteChanged(breed, isFavorite))
                }
            )
        }
    }
}

@Preview
@Composable
private fun FavoriteContentPreview(
    @PreviewParameter(BreedListParameterProvider::class)
    breedList: List<BreedUI>
) {
    CatChallengeThemeForPreview {
        FavoriteContent(
            isLoading = false,
            favoriteList = breedList,
            onEvent = {}
        )
    }
}