package com.ujizin.catchallenge.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.ujizin.catchallenge.core.ui.components.CenterLoading
import com.ujizin.catchallenge.core.ui.components.ImageCard
import com.ujizin.catchallenge.core.ui.model.BreedUI

@Composable
internal fun CatPagerList(
    pagingItems: LazyPagingItems<BreedUI>,
    modifier: Modifier = Modifier,
    onErrorRetryClick: () -> Unit,
    onBreedClick: (BreedUI) -> Unit,
    onFavoriteBreedChanged: (BreedUI, Boolean) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
            contentType = pagingItems.itemContentType { "CatPagerList" }
        ) { index ->
            val item = pagingItems[index] ?: return@items
            ImageCard(
                modifier = Modifier.animateItem(),
                name = item.name,
                imageUrl = item.imageUrl,
                isFavorite = item.isFavorite,
                onClick = { onBreedClick(item) },
                onFavoriteChanged = { onFavoriteBreedChanged(item, it) }
            )
        }

        when (pagingItems.loadState.append) {
            is LoadState.Error -> item(span = { GridItemSpan(2) }) {
                HomeErrorContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onRetryClick = onErrorRetryClick,
                )
            }

            LoadState.Loading -> item(span = { GridItemSpan(2) }) {
                CenterLoading(
                    modifier = Modifier.padding(top = 48.dp),
                )
            }

            is LoadState.NotLoading -> Unit
        }
    }
}
