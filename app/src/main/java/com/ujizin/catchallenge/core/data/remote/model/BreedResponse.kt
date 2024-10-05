package com.ujizin.catchallenge.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedResponse(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    @SerialName("reference_image_id")
    val imageId: String,
)
