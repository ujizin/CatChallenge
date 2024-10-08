package com.ujizin.catchallenge.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.ujizin.catchallenge.core.data.local.model.BreedEntity
import com.ujizin.catchallenge.core.data.utils.IgnoreKover
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds")
    fun getBreedsPagingSource(): PagingSource<Int, BreedEntity>

    @Query("SELECT * from breeds where favorite_id is NOT null")
    fun getFavoritesBreed(): Flow<List<BreedEntity>>

    @Query("SELECT * from breeds where id = :id")
    suspend fun findBreed(id: String): BreedEntity?

    @Update
    suspend fun updateBreed(breed: BreedEntity): Int

    @Query("UPDATE breeds set favorite_id = null")
    suspend fun removeAllFavorite()

    @Query("UPDATE breeds set favorite_id = :favoriteId where id = :id")
    suspend fun updateFavorite(id: String, favoriteId: Long)

    @Upsert
    suspend fun upsertAll(breeds: List<BreedEntity>)

    @Transaction
    @IgnoreKover
    suspend fun withTransaction(block: suspend BreedDao.() -> Unit) {
        block()
    }
}
