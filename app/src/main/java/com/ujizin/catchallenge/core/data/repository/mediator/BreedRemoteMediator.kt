package com.ujizin.catchallenge.core.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.datasource.BreedDataSource
import com.ujizin.catchallenge.core.data.repository.mapper.fromResponseToEntity
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator(
    private val breedDataSource: BreedDataSource,
    private val breedDao: BreedDao,
) : RemoteMediator<Int, BreedEntity>() {

    private var currentPage = 0

    private val <T: Any> PagingState<Int, T>.page: Int
        get() {
            val page = (pages.sumOf { it.data.size } / config.pageSize)
            return currentPage.coerceAtLeast(page)
        }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult = when (loadType) {
        LoadType.REFRESH,
        LoadType.PREPEND -> MediatorResult.Success(true)
        LoadType.APPEND -> try {
            currentPage = state.page
            val breedResponse = breedDataSource.getBreeds(
                limit = state.config.pageSize,
                page = currentPage++
            ).first()

            breedDao.withTransaction { upsertAll(breedResponse.fromResponseToEntity()) }

            MediatorResult.Success(endOfPaginationReached = breedResponse.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        internal const val PAGE_SIZE = 15
    }
}
