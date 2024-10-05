package com.ujizin.catchallenge.feature.home.ui.mapper

import com.ujizin.catchallenge.core.data.repository.model.Breed
import com.ujizin.catchallenge.feature.home.ui.model.BreedUI

fun Breed.toBreedUI() = BreedUI(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl
)
