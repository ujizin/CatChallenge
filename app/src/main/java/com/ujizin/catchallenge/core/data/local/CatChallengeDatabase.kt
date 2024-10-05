package com.ujizin.catchallenge.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import com.ujizin.catchallenge.core.data.local.model.BreedEntity

@Database(entities = [BreedEntity::class], version = 1)
abstract class CatChallengeDatabase: RoomDatabase() {

    abstract fun getBreedDao(): BreedDao

    companion object {
        internal const val LOCAL_DATABASE_NAME = "cat_challenge_db"
    }
}
