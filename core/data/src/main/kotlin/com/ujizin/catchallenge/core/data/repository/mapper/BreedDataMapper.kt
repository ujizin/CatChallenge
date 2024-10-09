package com.ujizin.catchallenge.core.data.repository.mapper

import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.remote.model.ImageId
import com.ujizin.catchallenge.core.model.Breed
import com.ujizin.catchallenge.core.data.BuildConfig

internal fun List<BreedResponse>.fromResponseToEntity(): List<BreedEntity> =
    map(BreedResponse::toEntity)

internal fun List<BreedResponse>.fromResponseToDomain(): List<Breed> = map(BreedResponse::toDomain)

internal fun BreedResponse.toEntity() = BreedEntity(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageId?.toImageUrl(),
    favoriteId = favoriteId,
    lifeSpan = lifeSpan,
)

internal fun BreedEntity.toDomain() = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl,
    lifeSpan = lifeSpan,
    isFavorite = favoriteId != null,
)

internal fun BreedResponse.toDomain() = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageId?.toImageUrl(),
    lifeSpan = lifeSpan,
    isFavorite = favoriteId != null
)

private fun ImageId.toImageUrl() = "${BuildConfig.CDN_URL}/$this"