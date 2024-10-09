package com.ujizin.catchallenge.core.test.utils

import com.ujizin.catchallenge.core.model.Breed

object BreedFeatureModelUtils {

    private const val DEFAULT_VALUE = 10

    fun createBreedList(size: Int = DEFAULT_VALUE) = List(size) { index ->
        Breed(
            id = "id-$index",
            name = "name-$index",
            description = "description-$index",
            origin = "origin-$index",
            temperament = "temperament-$index",
            imageUrl = "imageUrl-$index",
            isFavorite = false,
            lifeSpan = "12 - 15 - $index",
        )
    }

}