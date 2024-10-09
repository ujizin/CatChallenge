package com.ujizin.catchallenge.core.ui.mapper

import com.ujizin.catchallenge.core.model.Breed
import com.ujizin.catchallenge.core.ui.model.BreedUI

fun Breed.toBreedUI() = BreedUI(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    lifeSpan = lifeSpan,
)
