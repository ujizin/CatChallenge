package com.ujizin.catchallenge.core.data.remote.datasource

import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.remote.service.BreedService
import com.ujizin.catchallenge.core.data.remote.service.CatImageService
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
    private val imageService: CatImageService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {

    fun getBreeds(
        limit: Int,
        page: Int,
    ): Flow<List<BreedResponse>> = flow {
        emit(breedService.getBreeds(limit, page))
    }.map { breeds ->
        breeds.map breedMap@{ breed ->
            if (breed.imageId == null) {
                return@breedMap breed
            }
            breed.copy(imageUrl = imageService.getImage(breed.imageId).url)
        }
    }.flowOn(dispatcher)
}