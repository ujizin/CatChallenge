package com.ujizin.catchallenge.features.home.ui

import com.ujizin.catchallenge.core.ui.model.BreedUI

sealed interface HomeUIEvent {
    data class OnSearch(val text: String) : HomeUIEvent
    data class OnBreedClick(val breed: BreedUI) : HomeUIEvent
    data class OnBreedFavorite(val breed: BreedUI, val isFavorite: Boolean) : HomeUIEvent
}
