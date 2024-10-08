package com.ujizin.catchallenge.core.data.remote.datasource

import com.ujizin.catchallenge.core.data.remote.model.FavoritePayload
import com.ujizin.catchallenge.core.data.remote.model.FavoriteResponse
import com.ujizin.catchallenge.core.data.remote.provider.DeviceIdProvider
import com.ujizin.catchallenge.core.data.remote.service.FavoriteService
import com.ujizin.catchallenge.core.data.repository.dispatcher.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteDataSource @Inject constructor(
    private val favoriteService: FavoriteService,
    private val deviceIdProvider: DeviceIdProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    fun getFavorites() = flow {
        emit(favoriteService.getFavorites())
    }.map { favorites ->
        favorites.filter { it.userId == deviceIdProvider() }
    }.flowOn(dispatcher)

    fun sendFavorite(breedId: String, favoriteId: Long?): Flow<FavoriteResponse> = flow {
        when {
            favoriteId != null -> emit(FavoriteResponse(favoriteId))
            else -> emit(favoriteService.sendFavorite(FavoritePayload(breedId, deviceIdProvider())))
        }
    }.flowOn(dispatcher)

    fun deleteFavorite(favoriteId: Long?) = flow {
        checkNotNull(favoriteId) { "FavoriteId must not be null" }
        favoriteService.deleteFavorite(favoriteId)
        emit(true)
    }.catch { exception ->
        emit(exception is HttpException && exception.code() == HTTP_BAD_REQUEST)
    }.flowOn(dispatcher)
}
