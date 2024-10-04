package com.ujizin.catchallenge.core.navigation.destination

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Home: Destination

    @Serializable
    data object Favorite: Destination
}
