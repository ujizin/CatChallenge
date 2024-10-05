package com.ujizin.catchallenge.core.data.repository.mapper

import com.ujizin.catchallenge.BuildConfig
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.remote.model.ImageId
import com.ujizin.catchallenge.core.data.repository.model.Breed

internal fun List<BreedResponse>.toEntity(): List<BreedEntity> = map(BreedResponse::toEntity)

internal fun List<BreedEntity>.toDomain(): List<Breed> = map(BreedEntity::toDomain)

internal fun BreedResponse.toEntity() = BreedEntity(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageId?.toImageUrl(),
)

fun BreedEntity.toDomain() = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl
)

// TODO add cdn url
private fun ImageId.toImageUrl() = "${BuildConfig.BASE_URL}/v1/images/$this"