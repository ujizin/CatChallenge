package com.ujizin.catchallenge.core.data

import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.repository.model.Breed

object BreedDataModelUtils {

    private const val DEFAULT_VALUE = 10

    fun createBreedResponseList(size: Int = DEFAULT_VALUE) = List(size) { index ->
        BreedResponse(
            id = "id-$index",
            name = "name-$index",
            description = "description-$index",
            origin = "origin-$index",
            temperament = "temperament-$index",
            lifeSpan = "12 - 15"
        )
    }

    fun createBreedEntityList(size: Int = DEFAULT_VALUE) = List(size) { index ->
        BreedEntity(
            id = "id-$index",
            name = "name-$index",
            description = "description-$index",
            origin = "origin-$index",
            temperament = "temperament-$index",
            imageUrl = "imageUrl-$index",
            favoriteId = index.toLong(),
            lifeSpan = "12 - 15 - $index",
        )
    }

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

    fun Breed.toEntity() = BreedEntity(
        id = id,
        name = name,
        description = description,
        origin = origin,
        temperament = temperament,
        imageUrl = imageUrl,
        favoriteId = null,
        lifeSpan = "12 - 15",
    )
}
