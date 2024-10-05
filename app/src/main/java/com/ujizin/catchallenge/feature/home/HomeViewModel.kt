package com.ujizin.catchallenge.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.data.repository.model.Breed
import com.ujizin.catchallenge.core.ui.utils.WhileActivate
import com.ujizin.catchallenge.feature.home.ui.HomeUIEvent
import com.ujizin.catchallenge.feature.home.ui.HomeUIState
import com.ujizin.catchallenge.feature.home.ui.mapper.toBreedUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: BreedRepository
) : ViewModel() {

    private val _searchTextState = MutableStateFlow("")

    private val paging = repository.pager
        .map { pagingData -> pagingData.map(Breed::toBreedUI) }
        .cachedIn(viewModelScope)

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

    fun onEvent(event: HomeUIEvent): Unit = when(event) {
        is HomeUIEvent.OnSearch -> _searchTextState.update { event.text }
        else -> Unit
    }
}
