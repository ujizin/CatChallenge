package com.ujizin.catchallenge.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.model.Breed
import com.ujizin.catchallenge.core.ui.mapper.toBreedUI
import com.ujizin.catchallenge.core.ui.model.BreedUI
import com.ujizin.catchallenge.core.ui.utils.WhileActivate
import com.ujizin.catchallenge.features.home.ui.HomeUIEvent
import com.ujizin.catchallenge.features.home.ui.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class HomeViewModel @Inject constructor(
    private val repository: BreedRepository
) : ViewModel() {

    private val _searchTextState = MutableStateFlow("")
    private val _loadingState = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchBreeds = _searchTextState.flatMapConcat { searchText ->
        repository.searchBreeds(searchText).debounce(DEBOUNCE_TIME)
            .onStart { _loadingState.update { true } }
            .onCompletion { _loadingState.update { false } }
    }.map {
        it.getOrNull()?.map(Breed::toBreedUI).orEmpty()
    }

    private val paging = repository.pager
        .map { it.map(Breed::toBreedUI) }
        .cachedIn(viewModelScope)
        .combine(searchBreeds) { pagingData, searchBreeds ->
            when {
                searchBreeds.isNotEmpty() -> PagingData.from(searchBreeds)
                else -> pagingData.filterSearchText(_searchTextState.value)
            }
        }

    private fun PagingData<BreedUI>.filterSearchText(searchText: String) = filter {
        searchText.isBlank() || it.name.contains(searchText, ignoreCase = true)
    }

    val uiState = combine(_searchTextState, _loadingState) { searchText, isLoading ->
        HomeUIState(
            isLoading = isLoading,
            paging = paging,
            searchText = searchText,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileActivate,
        HomeUIState()
    )

    fun onEvent(event: HomeUIEvent): Unit = when (event) {
        is HomeUIEvent.OnSearch -> _searchTextState.update { event.text }
        is HomeUIEvent.OnBreedFavorite -> onBreedFavorite(event)

        is HomeUIEvent.OnBreedClick -> Unit
    }

    private fun onBreedFavorite(event: HomeUIEvent.OnBreedFavorite) {
        repository.updateFavorite(
            id = event.breed.id,
            isFavorite = event.isFavorite,
        ).launchIn(viewModelScope)
    }

    companion object {
        private const val DEBOUNCE_TIME = 200L
    }
}
