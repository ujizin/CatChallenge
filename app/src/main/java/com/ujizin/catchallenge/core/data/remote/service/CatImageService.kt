package com.ujizin.catchallenge.core.data.remote.service

import com.ujizin.catchallenge.core.data.remote.model.CatImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CatImageService {

    @GET("/v1/images/{id}")
    suspend fun getImage(@Path("id") id: String): CatImageResponse
}
