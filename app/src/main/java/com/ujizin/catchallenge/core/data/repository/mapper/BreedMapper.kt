package com.ujizin.catchallenge.core.data.repository.mapper

import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.repository.model.Breed

internal fun List<BreedResponse>.fromResponseToEntity(): List<BreedEntity> = map(BreedResponse::toEntity)

internal fun  List<BreedResponse>.fromResponseToDomain(): List<Breed> = map(BreedResponse::toDomain)

internal fun BreedResponse.toEntity() = BreedEntity(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl,
)

internal fun BreedEntity.toDomain() = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl
)

internal fun BreedResponse.toDomain() = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl,
)
