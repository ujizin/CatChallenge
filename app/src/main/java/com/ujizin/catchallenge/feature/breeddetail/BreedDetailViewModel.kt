package com.ujizin.catchallenge.feature.breeddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.navigation.destination.Destination
import com.ujizin.catchallenge.core.navigation.destination.Destination.BreedDetail.Companion.typeMap
import com.ujizin.catchallenge.core.ui.utils.WhileActivate
import com.ujizin.catchallenge.feature.breeddetail.ui.BreedDetailUIEvent
import com.ujizin.catchallenge.feature.breeddetail.ui.BreedDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val breedDetailRoute = savedStateHandle.toRoute<Destination.BreedDetail>(typeMap)

    private val _breedState = MutableStateFlow(breedDetailRoute.breed)

    val uiState: StateFlow<BreedDetailUIState> = _breedState.map { breed ->
        BreedDetailUIState(breed)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileActivate,
        BreedDetailUIState(breedDetailRoute.breed)
    )

    fun onEvent(event: BreedDetailUIEvent): Unit = when (event) {
        is BreedDetailUIEvent.OnFavoriteChanged -> onFavoriteChanged(event.isFavorite)
        BreedDetailUIEvent.BackPressed -> Unit
    }

    private fun onFavoriteChanged(isFavorite: Boolean) {
        breedRepository.updateFavorite(
            id = breedDetailRoute.breed.id,
            isFavorite = isFavorite,
        ).launchIn(viewModelScope)

        _breedState.update { it.copy(isFavorite = isFavorite) }
    }
}
