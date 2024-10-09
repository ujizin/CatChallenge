package com.ujizin.catchallenge.features.breeddetail.ui

sealed interface BreedDetailUIEvent {
    data object BackPressed: BreedDetailUIEvent
    data class OnFavoriteChanged(val isFavorite: Boolean): BreedDetailUIEvent
}
