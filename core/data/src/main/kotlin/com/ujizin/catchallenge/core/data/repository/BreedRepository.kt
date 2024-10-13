package com.ujizin.catchallenge.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.datasource.BreedDataSource
import com.ujizin.catchallenge.core.data.remote.datasource.FavoriteDataSource
import com.ujizin.catchallenge.core.data.remote.model.BreedResponse
import com.ujizin.catchallenge.core.data.repository.dispatcher.IoDispatcher
import com.ujizin.catchallenge.core.data.repository.mapper.toDomain
import com.ujizin.catchallenge.core.data.repository.mediator.BreedRemoteMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalCoroutinesApi::class)
class BreedRepository @Inject constructor(
    private val breedDataSource: BreedDataSource,
    private val favoriteDataSource: FavoriteDataSource,
    private val breedDao: BreedDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val limitedDispatch = dispatcher.limitedParallelism(PARALLELISM_THREADS)

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(pageSize = BreedRemoteMediator.PAGE_SIZE),
        remoteMediator = BreedRemoteMediator(
            breedDataSource = breedDataSource,
            breedDao = breedDao,
        ),
        pagingSourceFactory = breedDao::getBreedsPagingSource
    ).flow.map { pagingData ->
        pagingData.map(BreedEntity::toDomain)
    }.flowOn(dispatcher)

    fun searchBreeds(search: String) = breedDataSource.searchBreeds(search)
        .onStart { check(search.isNotBlank()) { "Search text must not be null" } }
        .map { breeds ->
            Result.success(breeds.map(BreedResponse::toDomain))
        }.catch {
            emit(Result.failure(it))
        }.flowOn(dispatcher)

    fun getFavorites() = breedDao.getFavoritesBreed().map { localFavorites ->
        localFavorites.map(BreedEntity::toDomain)
    }.flowOn(dispatcher)

    fun updateFavorite(
        id: String,
        isFavorite: Boolean
    ): Flow<Boolean> = flow { emit(breedDao.findBreed(id)) }
        .mapNotNull { checkNotNull(it) { "Breed must not be null to update" } }
        .flatMapConcat { breed -> updateFavoriteInternal(breed, isFavorite) }
        .flowOn(limitedDispatch)

    internal fun getRemoteFavorites() = favoriteDataSource
        .getFavorites()
        .flowOn(dispatcher)

    internal fun updateFavoriteInternal(
        breed: BreedEntity,
        isFavorite: Boolean,
    ): Flow<Boolean> = when {
        isFavorite -> favoriteDataSource.sendFavorite(
            breedId = breed.id,
            favoriteId = breed.favoriteId,
        ).map { favorite -> favorite.id }

        else -> favoriteDataSource.deleteFavorite(breed.favoriteId).map { result ->
            breed.favoriteId.takeUnless { result }
        }
    }.onStart {
        breedDao.updateBreed(breed.copy(isFavorite = isFavorite))
    }.map { favoriteId ->
        breedDao.updateFavorite(breed.id, favoriteId)
        true
    }.catch {
        emit(false)
    }.flowOn(limitedDispatch)

    companion object {
        private const val PARALLELISM_THREADS = 1
    }
}
