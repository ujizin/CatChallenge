package com.ujizin.catchallenge.feature.favorites.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ujizin.catchallenge.core.ui.R
import com.ujizin.catchallenge.core.ui.components.ImageCard
import com.ujizin.catchallenge.core.ui.model.BreedUI

@Composable
fun LazyGridListBreed(
    modifier: Modifier,
    breedList: List<BreedUI>,
    onFavoriteChanged: (BreedUI, Boolean) -> Unit,
    onBreedItemClick: (BreedUI) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = breedList,
            key = { it.id },
            contentType = { "FavoriteContent" },
        ) { breed ->
            ImageCard(
                modifier = Modifier.animateItem(),
                name = breed.name,
                subLabel = stringResource(R.string.life_span_label, breed.lifeSpan),
                imageUrl = breed.imageUrl,
                onClick = { onBreedItemClick(breed) },
                isFavorite = breed.isFavorite,
                onFavoriteChanged = { onFavoriteChanged(breed, it) },
            )
        }
    }
}