package com.example.tmobileproject.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tmobileproject.data.local.model.CardEntity
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CardDao {

    @Upsert
    suspend fun upsertCardList(cardList: List<CardEntity>)

    @Query("SELECT * FROM cardentity")
    suspend fun  getCards(): List<CardEntity>
}