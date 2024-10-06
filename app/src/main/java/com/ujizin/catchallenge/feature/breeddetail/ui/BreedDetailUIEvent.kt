package com.ujizin.catchallenge.feature.breeddetail.ui

sealed interface BreedDetailUIEvent {
    data object BackPressed: BreedDetailUIEvent
    data class OnFavoriteChanged(val isFavorite: Boolean): BreedDetailUIEvent
}
