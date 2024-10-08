package com.ujizin.catchallenge.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ujizin.catchallenge.core.ui.model.BreedUI

class BreedListParameterProvider: PreviewParameterProvider<List<BreedUI>> {

    override val values: Sequence<List<BreedUI>>
        get() = sequenceOf(
            emptyList(),
            List(10) {
                BreedUI(
                    id = "id - $it",
                    name = "name - $it",
                    origin = "origin - $it",
                    temperament = "temperament - $it",
                    description = "description - $it",
                    imageUrl = null,
                    isFavorite = it % 2 == 0,
                    lifeSpan = "10 - 1$it"
                )
            }
        )
}