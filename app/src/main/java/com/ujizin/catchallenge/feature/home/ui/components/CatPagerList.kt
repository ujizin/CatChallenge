package com.ujizin.catchallenge.feature.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ujizin.catchallenge.core.ui.components.ImageCard
import com.ujizin.catchallenge.core.ui.components.CenterLoading
import com.ujizin.catchallenge.feature.home.ui.model.BreedUI

@Composable
internal fun CatPagerList(
    modifier: Modifier,
    pagingItems: LazyPagingItems<BreedUI>,
    onBreedClick: (id: String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(count = pagingItems.itemCount) { index ->
            val item = pagingItems[index] ?: return@items
            ImageCard(
                name = item.name,
                imageUrl = item.imageUrl,
                onClick = { onBreedClick(item.id) }
            )
        }

        when (pagingItems.loadState.append) {
            is LoadState.Error -> item(span = { GridItemSpan(2) }) {
                // TODO to be implemented
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
