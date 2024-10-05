package com.ujizin.catchallenge.feature.home.ui

sealed interface HomeUIEvent {
    data class OnSearch(val text: String) : HomeUIEvent
    data class OnBreedClick(val id: String): HomeUIEvent
}
