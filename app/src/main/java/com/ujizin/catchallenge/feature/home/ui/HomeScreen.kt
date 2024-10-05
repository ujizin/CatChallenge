package com.ujizin.catchallenge.feature.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.ujizin.catchallenge.R
import com.ujizin.catchallenge.core.ui.components.CenterLoading
import com.ujizin.catchallenge.core.ui.components.TextField
import com.ujizin.catchallenge.core.ui.components.TopAppBarContent
import com.ujizin.catchallenge.core.ui.theme.CatChallengeTheme
import com.ujizin.catchallenge.feature.home.HomeViewModel
import com.ujizin.catchallenge.feature.home.ui.components.CatPagerList
import com.ujizin.catchallenge.feature.home.ui.model.BreedUI
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    onNavigateToBreedDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        modifier = Modifier.fillMaxSize(),
        isLoading = uiState.isLoading,
        pagingData = uiState.paging,
        searchText = uiState.searchText,
        onEvent = { event ->
            when (event) {
                is HomeUIEvent.OnBreedClick -> onNavigateToBreedDetail(event.id)
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun HomeContent(
    isLoading: Boolean,
    pagingData: Flow<PagingData<BreedUI>>,
    searchText: String,
    onEvent: (HomeUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagingItems = pagingData.collectAsLazyPagingItems()

    TopAppBarContent(
        header = {
            Text(
                text = stringResource(R.string.home_label),
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                value = searchText,
                placeholder = { Text(stringResource(R.string.search_label)) },
                onValueChange = { onEvent(HomeUIEvent.OnSearch(it)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
            )
        },
        content = {
            when {
                isLoading -> CenterLoading(modifier = Modifier.fillMaxSize())
                else -> CatPagerList(
                    modifier = modifier,
                    pagingItems = pagingItems,
                    onBreedClick = { onEvent(HomeUIEvent.OnBreedClick(it)) }
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeContentPreview() {
    CatChallengeTheme {
        val pagingDataFlow = snapshotFlow { PagingData.empty<BreedUI>() }
        HomeContent(
            isLoading = true,
            pagingData = pagingDataFlow,
            searchText = "",
            onEvent = {}
        )
    }
}
