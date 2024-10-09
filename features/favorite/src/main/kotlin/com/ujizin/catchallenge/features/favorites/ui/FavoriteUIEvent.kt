package com.ujizin.catchallenge.features.favorites.ui

import com.ujizin.catchallenge.core.ui.model.BreedUI

sealed interface FavoriteUIEvent {
    data class OnBreedClick(val breed: BreedUI) : FavoriteUIEvent
    data class OnFavoriteChanged(val breed: BreedUI, val isFavorite: Boolean): FavoriteUIEvent
}
