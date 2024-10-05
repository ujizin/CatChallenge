package com.ujizin.catchallenge.feature.home.ui

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.ujizin.catchallenge.core.ui.model.BreedUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class HomeUIState(
    val isLoading: Boolean = true,
    val paging: Flow<PagingData<BreedUI>> = emptyFlow(),
    val searchText: String = "",
)
