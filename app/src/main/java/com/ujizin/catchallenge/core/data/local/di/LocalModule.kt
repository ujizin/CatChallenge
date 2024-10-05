package com.ujizin.catchallenge.core.data.local.di

import android.content.Context
import androidx.room.Room
import com.ujizin.catchallenge.core.data.local.CatChallengeDatabase
import com.ujizin.catchallenge.core.data.local.CatChallengeDatabase.Companion.LOCAL_DATABASE_NAME
import com.ujizin.catchallenge.core.data.local.dao.BreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CatChallengeDatabase = Room.databaseBuilder(
        context,
        CatChallengeDatabase::class.java,
        LOCAL_DATABASE_NAME
    ).build()

    @Provides
    fun provideBreedDao(
        database: CatChallengeDatabase
    ): BreedDao = database.getBreedDao()
}
