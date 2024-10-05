package com.ujizin.catchallenge.feature.home.ui

import com.ujizin.catchallenge.core.ui.model.BreedUI

sealed interface HomeUIEvent {
    data class OnSearch(val text: String) : HomeUIEvent
    data class OnBreedClick(val breed: BreedUI): HomeUIEvent
}
