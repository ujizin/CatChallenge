package com.ujizin.catchallenge.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.ui.model.BreedUI
import com.ujizin.catchallenge.core.ui.utils.WhileActivate
import com.ujizin.catchallenge.feature.home.ui.HomeUIEvent
import com.ujizin.catchallenge.feature.home.ui.HomeUIState
import com.ujizin.catchallenge.feature.home.ui.mapper.toBreedUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
@OptIn(FlowPreview::class)
class HomeViewModel @Inject constructor(
    private val repository: BreedRepository
) : ViewModel() {

    private val _searchTextState = MutableStateFlow("")
    private val _itemsFavorites = MutableStateFlow(setOf<BreedUI>())

    private val paging = repository.pager
        .cachedIn(viewModelScope)
        .combine(_searchTextState.debounce(DEBOUNCE_TIME)) { pagingData, searchText ->
            pagingData.filter {
                searchText.isBlank() || it.name.startsWith(searchText, ignoreCase = true)
            }
        }
        .combine(_itemsFavorites) { pagingData, itemsFavorite ->
            pagingData.map { pagingBreed ->
                val breedUI = pagingBreed.toBreedUI()
                itemsFavorite.find { it.id == breedUI.id } ?: breedUI
            }
        }

    val uiState = _searchTextState.map { searchText ->
        HomeUIState(
            isLoading = false,
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
        val updatedBreed = event.breed.copy(isFavorite = event.isFavorite)

        repository.updateFavorite(
            id = updatedBreed.id,
            isFavorite = updatedBreed.isFavorite
        ).launchIn(viewModelScope)

        _itemsFavorites.update { list ->
            (list - event.breed) + updatedBreed
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 200L
    }
}
