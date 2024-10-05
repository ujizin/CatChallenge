package com.ujizin.catchallenge.feature.home.ui

import androidx.paging.PagingData
import com.ujizin.catchallenge.feature.home.ui.model.BreedUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

data class HomeUIState(
    val isLoading: Boolean = true,
    val paging: Flow<PagingData<BreedUI>> = emptyFlow(),
    val searchText: String = "",
)
