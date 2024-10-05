package com.ujizin.catchallenge.core.data.remote.service

import com.ujizin.catchallenge.core.data.remote.model.FavoritePayload
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteService {

    @GET("/favourites")
    suspend fun getFavorites(): List<FavoriteResponse>

    @POST("/favourites")
    suspend fun sendFavorite(
        @Body payload: FavoritePayload
    )

    @DELETE("/favourites/{id}")
    suspend fun deleteFavorite(
        @Path("id") id: Int
    )
}
