package com.nexters.emotia.core.database.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

actual class DatabaseFactory(private val context: Context) {
    actual fun createDatabase(): EmotiaDatabase {
        val dbFile = context.getDatabasePath(EmotiaDatabase.DATABASE_NAME)
        return Room.databaseBuilder<EmotiaDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
