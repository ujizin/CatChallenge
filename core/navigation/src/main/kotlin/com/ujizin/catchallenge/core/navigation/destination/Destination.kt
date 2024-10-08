package com.ujizin.catchallenge.core.navigation.destination

import com.ujizin.catchallenge.core.navigation.navtype.BreedNavType
import com.ujizin.catchallenge.core.ui.model.BreedUI
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
sealed interface Destination {
    @Serializable
    data object Home : Destination

    @Serializable
    data object Favorite : Destination

    @Serializable
    data class BreedDetail(val breed: BreedUI) : Destination {
        companion object {
            val typeMap = mapOf(typeOf<BreedUI>() to BreedNavType)
        }
    }
}
