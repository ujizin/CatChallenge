package com.ujizin.catchallenge.core.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteResponse(
    val id: Int,
    @SerialName("image_id")
    val imageId: String,
    val image: ImageResponse,
) {

    @Serializable
    data class ImageResponse(
        val id: String,
        val url: String
    )
}
