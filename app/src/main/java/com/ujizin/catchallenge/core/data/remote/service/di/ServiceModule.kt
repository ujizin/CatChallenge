package com.ujizin.catchallenge.core.data.remote.service.di

import com.ujizin.catchallenge.core.data.remote.service.BreedService
import com.ujizin.catchallenge.core.data.remote.service.FavoriteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideBreedService(retrofit: Retrofit) = retrofit.create(BreedService::class.java)

    @Provides
    @Singleton
    fun provideFavoriteService(retrofit: Retrofit) = retrofit.create(FavoriteService::class.java)

}
