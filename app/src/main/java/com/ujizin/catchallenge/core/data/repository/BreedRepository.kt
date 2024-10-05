package com.ujizin.catchallenge.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.datasource.BreedDataSource
import com.ujizin.catchallenge.core.data.repository.dispatcher.IoDispatcher
import com.ujizin.catchallenge.core.data.repository.mapper.toDomain
import com.ujizin.catchallenge.core.data.repository.mediator.BreedRemoteMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedRepository @Inject constructor(
    breedDataSource: BreedDataSource,
    breedDao: BreedDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

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
}
