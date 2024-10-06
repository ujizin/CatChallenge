package com.ujizin.catchallenge.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.datasource.BreedDataSource
import com.ujizin.catchallenge.core.data.remote.datasource.FavoriteDataSource
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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalCoroutinesApi::class)
class BreedRepository @Inject constructor(
    breedDataSource: BreedDataSource,
    private val favoriteDataSource: FavoriteDataSource,
    private val breedDao: BreedDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val mutex = Mutex()

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

    fun updateFavorite(
        id: String,
        isFavorite: Boolean
    ): Flow<Boolean> = flow { emit(breedDao.findBreed(id)) }
        .onStart { mutex.lock() }
        .mapNotNull { checkNotNull(it) { "Favorite breed must not be null to delete" } }
        .flatMapConcat { breed -> updateFavoriteInternal(breed, isFavorite) }
        .map { breed -> breedDao.updateBreed(breed) != -1 }
        .catch { emit(false) }
        .onCompletion { mutex.unlock() }
        .flowOn(dispatcher)

    private fun updateFavoriteInternal(
        breed: BreedEntity,
        isFavorite: Boolean
    ): Flow<BreedEntity> = when {
        isFavorite -> favoriteDataSource.sendFavorite(breed.id, breed.favoriteId).map { it.id }
        else -> favoriteDataSource.deleteFavorite(breed.favoriteId).map { result ->
            breed.favoriteId.takeUnless { result }
        }
    }.map { favoriteId -> breed.copy(favoriteId = favoriteId) }
}
