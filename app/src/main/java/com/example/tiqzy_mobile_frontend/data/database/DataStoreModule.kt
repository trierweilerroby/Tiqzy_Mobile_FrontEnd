package com.example.tiqzy_mobile_frontend.data.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreSingleton(
        @ApplicationContext context: Context
    ): DataStoreSingleton {
        return DataStoreSingleton(context)
    }
}

