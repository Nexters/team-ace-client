package com.nexters.emotia.core.database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.nexters.emotia.core.database.dao.ChatMessageDao
import com.nexters.emotia.core.database.entity.ChatMessageEntity

@ConstructedBy(EmotiaDatabaseConstructor::class)
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

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object EmotiaDatabaseConstructor : RoomDatabaseConstructor<EmotiaDatabase> {
    override fun initialize(): EmotiaDatabase
}