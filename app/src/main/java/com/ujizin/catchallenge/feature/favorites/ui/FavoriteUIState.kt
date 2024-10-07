package com.ujizin.catchallenge.feature.favorites.ui

import androidx.compose.runtime.Immutable
import com.ujizin.catchallenge.core.ui.model.BreedUI

@Immutable
data class FavoriteUIState(
    val isLoading: Boolean = true,
    val favoriteList: List<BreedUI> = emptyList(),
)
