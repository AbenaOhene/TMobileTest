package com.example.tmobileproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmobileproject.data.local.model.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HomeFeedDatabase: RoomDatabase() {
    abstract  val dao: CardDao
}