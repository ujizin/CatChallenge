package com.ujizin.catchallenge.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ujizin.catchallenge.core.ui.model.BreedUI

class BreedParameterProvider : PreviewParameterProvider<BreedUI> {

    private val initialBreed = BreedUI(
        id = "id",
        name = "name",
        origin = "origin",
        temperament = "temperament",
        description = "description",
        imageUrl = null,
        isFavorite = false,
        lifeSpan = "10 - 15"
    )

    override val values: Sequence<BreedUI>
        get() = sequenceOf(
            initialBreed,
            initialBreed.copy(isFavorite = true)
        )
}
