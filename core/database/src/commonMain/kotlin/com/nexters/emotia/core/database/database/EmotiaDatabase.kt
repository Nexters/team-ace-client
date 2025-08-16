package com.nexters.emotia.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexters.emotia.core.database.dao.ChatMessageDao
import com.nexters.emotia.core.database.entity.ChatMessageEntity

@Database(
    entities = [ChatMessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EmotiaDatabase : RoomDatabase() {
    abstract fun chatMessageDao(): ChatMessageDao

    companion object {
        const val DATABASE_NAME = "emotia_database.db"
    }
}