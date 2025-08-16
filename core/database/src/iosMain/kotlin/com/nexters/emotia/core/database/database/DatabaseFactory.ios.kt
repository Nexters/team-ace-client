package com.nexters.emotia.core.database.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSHomeDirectory

actual class DatabaseFactory {
    actual fun createDatabase(): EmotiaDatabase {
        val dbFilePath = documentDirectory() + "/${EmotiaDatabase.DATABASE_NAME}"
        return Room.databaseBuilder<EmotiaDatabase>(
            name = dbFilePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSHomeDirectory() + "/Documents"
    return documentDirectory
}