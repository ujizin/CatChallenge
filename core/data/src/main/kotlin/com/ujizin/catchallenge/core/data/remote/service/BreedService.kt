package com.ujizin.catchallenge.core.data.remote.service

import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedService {

    @GET("v1/breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): List<BreedResponse>
}
