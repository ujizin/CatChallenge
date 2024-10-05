package com.ujizin.catchallenge.core.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.service.BreedService
import com.ujizin.catchallenge.core.data.repository.mapper.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class BreedRemoteMediator @Inject constructor(
    private val breedDao: BreedDao,
    private val breedService: BreedService,
) : RemoteMediator<Int, BreedEntity>() {

    private var currentPage: Int = 0

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult = when(loadType) {
        LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
        else -> try {
            val breedResponse = breedService.getBreeds(PAGE_SIZE, currentPage++)
            breedDao.upsertAll(breedResponse.toEntity())
            MediatorResult.Success(endOfPaginationReached = breedResponse.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        internal const val PAGE_SIZE = 20
    }
}

