package com.ujizin.catchallenge.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ujizin.catchallenge.core.data.local.model.BreedEntity

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds")
    fun getBreedsPagingSource(): PagingSource<Int, BreedEntity>

    @Upsert
    suspend fun upsertAll(breeds: List<BreedEntity>)
}
