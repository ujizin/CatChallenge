package com.ujizin.catchallenge.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritePayload(
    @SerialName("image_id")
    val imageId: String,
)
