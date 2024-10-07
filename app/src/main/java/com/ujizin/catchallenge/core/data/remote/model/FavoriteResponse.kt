package com.ujizin.catchallenge.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    val id: Long,
    @SerialName("image_id")
    val breedId: String? = null,
)
