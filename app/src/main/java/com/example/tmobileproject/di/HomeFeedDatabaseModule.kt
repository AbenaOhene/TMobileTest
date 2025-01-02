package com.example.tmobileproject.di

import android.content.Context
import androidx.room.Room
import com.example.tmobileproject.data.local.CardDao
import com.example.tmobileproject.data.local.HomeFeedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeFeedDatabaseModule {

    @Provides
    @Singleton
    fun provideHomeFeedDatabase(
        @ApplicationContext context: Context,
    ): HomeFeedDatabase =
        Room.databaseBuilder(
            context,
            HomeFeedDatabase::class.java,
            "card-data"
        )
            .build()

    @Provides
    fun provideCardDao(
        homeFeedDatabase: HomeFeedDatabase,
    ): CardDao = homeFeedDatabase.dao
}