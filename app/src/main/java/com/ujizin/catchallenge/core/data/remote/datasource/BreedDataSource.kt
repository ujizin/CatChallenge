package com.ujizin.catchallenge.core.data.remote.datasource

import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.remote.service.BreedService
import com.ujizin.catchallenge.core.data.remote.service.FavoriteService
import com.ujizin.catchallenge.core.data.repository.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedDataSource @Inject constructor(
    private val breedService: BreedService,
    private val favoriteService: FavoriteService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {

    fun getBreeds(
        limit: Int,
        page: Int,
    ): Flow<List<BreedResponse>> = flow {
        emit(breedService.getBreeds(limit, page))
    }.mapFavorites().flowOn(dispatcher)

    private fun Flow<List<BreedResponse>>.mapFavorites() = map { breeds ->
        val favorites = favoriteService.getFavorites()
        breeds.map breedsMap@{ breed ->
            breed.copy(favoriteId = favorites.find { it.breedId == breed.id }?.id)
        }
    }
}
