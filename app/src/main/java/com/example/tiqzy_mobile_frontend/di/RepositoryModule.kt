package com.example.tiqzy_mobile_frontend.di

import com.example.tiqzy_mobile_frontend.data.network.EventApiService
import com.example.tiqzy_mobile_frontend.data.repository.CategoryRepository
import com.example.tiqzy_mobile_frontend.data.repository.CitiesRepository
import com.example.tiqzy_mobile_frontend.data.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(): CategoryRepository {
        return CategoryRepository()
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventApiService: EventApiService): EventRepository {
        return EventRepository  (eventApiService)
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(): CitiesRepository {
        return CitiesRepository()
    }
}
