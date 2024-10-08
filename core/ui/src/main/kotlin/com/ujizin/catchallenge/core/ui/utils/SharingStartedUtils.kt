package com.ujizin.catchallenge.core.ui.utils

import kotlinx.coroutines.flow.SharingStarted

val SharingStarted.Companion.WhileActivate get() = WhileSubscribed(5_000)