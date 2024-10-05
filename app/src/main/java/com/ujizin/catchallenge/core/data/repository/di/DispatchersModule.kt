package com.ujizin.catchallenge.core.data.repository.di

import com.ujizin.catchallenge.core.data.repository.dispatcher.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @IoDispatcher
    fun providesIODispatcher() = Dispatchers.IO
}
