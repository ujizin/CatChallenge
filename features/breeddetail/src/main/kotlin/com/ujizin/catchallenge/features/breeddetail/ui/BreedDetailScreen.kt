package com.ujizin.catchallenge.features.breeddetail.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ujizin.catchallenge.core.ui.components.FixedAppBar
import com.ujizin.catchallenge.core.ui.composition.LocalNavAnimatedVisibilityScope
import com.ujizin.catchallenge.core.ui.composition.LocalSharedTransitionScope
import com.ujizin.catchallenge.core.ui.model.BreedUI
import com.ujizin.catchallenge.core.ui.preview.BreedParameterProvider
import com.ujizin.catchallenge.core.ui.theme.CatChallengeThemeForPreview
import com.ujizin.catchallenge.features.breeddetail.BreedDetailViewModel
import com.ujizin.catchallenge.features.breeddetail.ui.components.BreedDetailContainer

@Composable
fun BreedDetailScreen(
    onBackPressed: () -> Unit,
    viewModel: BreedDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    BreedDetailContent(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        breedUI = uiState.breed,
        onEvent = { event ->
            when (event) {
                BreedDetailUIEvent.BackPressed -> onBackPressed()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BreedDetailContent(
    breedUI: BreedUI,
    onEvent: (BreedDetailUIEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    FixedAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { onEvent(BreedDetailUIEvent.BackPressed) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        title = { Text(breedUI.name) }
    ) {
        AsyncImage(
            modifier = with(LocalSharedTransitionScope.current) {
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
                    .sharedElement(
                        state = rememberSharedContentState(breedUI.name),
                        animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current,
                        clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(0.dp)),
                    )
            },
            model = ImageRequest.Builder(LocalContext.current)
                .data(breedUI.imageUrl)
                .build(),
            contentDescription = breedUI.name,
            contentScale = ContentScale.Crop
        )
        BreedDetailContainer(
            modifier = Modifier.padding(16.dp),
            name = breedUI.name,
            description = breedUI.description,
            temperament = breedUI.temperament,
            origin = breedUI.origin,
            isFavorite = breedUI.isFavorite,
            onFavoriteChanged = { onEvent(BreedDetailUIEvent.OnFavoriteChanged(it)) },
        )
    }
}

@Preview
@Composable
private fun BreedDetailContentPreview(
    @PreviewParameter(BreedParameterProvider::class) breedUI: BreedUI,
) {
    CatChallengeThemeForPreview {
        BreedDetailContent(
            breedUI = breedUI,
            onEvent = {},
        )
    }
}
