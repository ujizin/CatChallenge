package com.ujizin.catchallenge.core.model

data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageUrl: String?,
    val isFavorite: Boolean,
    val lifeSpan: String,
)
