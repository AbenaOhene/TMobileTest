package com.example.tmobileproject.di

import com.example.tmobileproject.data.local.CardDao
import com.example.tmobileproject.data.remote.network.HomeFeedApi
import com.example.tmobileproject.data.repo.HomeFeedRepository
import com.example.tmobileproject.data.repo.HomeFeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesSchoolRepository (
        apiService: HomeFeedApi,
        dao: CardDao
    ): HomeFeedRepository = HomeFeedRepositoryImpl(apiService, dao)
}