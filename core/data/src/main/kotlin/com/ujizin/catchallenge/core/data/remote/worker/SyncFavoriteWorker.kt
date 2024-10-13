package com.ujizin.catchallenge.core.data.remote.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import com.ujizin.catchallenge.core.data.repository.BreedRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@HiltWorker
class SyncFavoriteWorker @AssistedInject constructor(
    private val breedDao: BreedDao,
    private val repository: BreedRepository,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = combine(
        breedDao.getUnsyncedFavorites(),
        repository.getRemoteFavorites()
    ) { unsyncFavorites, remoteFavorites ->
        unsyncFavorites.syncOfflineFavorites()
        remoteFavorites.syncRemoteFavorites()
    }.map {
        Result.success()
    }.catch {
        emit(Result.failure())
    }.first()

    private suspend fun List<BreedEntity>.syncOfflineFavorites() = forEach { favorite ->
        repository.updateFavoriteInternal(favorite, favorite.isFavorite).first()
    }

    private suspend fun List<FavoriteResponse>.syncRemoteFavorites() = forEach { favorite ->
        favorite.breedId?.let { breedId -> breedDao.updateFavorite(breedId, favorite.id) }
    }
}
