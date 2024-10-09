package com.ujizin.catchallenge.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import com.ujizin.catchallenge.core.model.Breed
import com.ujizin.catchallenge.core.ui.mapper.toBreedUI
import com.ujizin.catchallenge.core.ui.model.BreedUI
import com.ujizin.catchallenge.core.ui.utils.WhileActivate
import com.ujizin.catchallenge.features.favorites.ui.FavoriteUIEvent
import com.ujizin.catchallenge.features.favorites.ui.FavoriteUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: BreedRepository,
) : ViewModel() {

    val uiState = repository.getFavorites().map {
        FavoriteUIState(isLoading = false, it.map(Breed::toBreedUI))
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileActivate,
        FavoriteUIState()
    )

    fun onEvent(event: FavoriteUIEvent) = when (event) {
        is FavoriteUIEvent.OnFavoriteChanged -> onFavoriteChanged(
            breed = event.breed,
            isFavorite = event.isFavorite,
        )

        is FavoriteUIEvent.OnBreedClick -> Unit
    }

    private fun onFavoriteChanged(
        breed: BreedUI,
        isFavorite: Boolean,
    ) {
        repository.updateFavorite(
            id = breed.id,
            isFavorite = isFavorite,
        ).launchIn(viewModelScope)
    }
}
