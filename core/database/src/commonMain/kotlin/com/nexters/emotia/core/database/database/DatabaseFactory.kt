package com.nexters.emotia.core.database.database

expect class DatabaseFactory {
    fun createDatabase(): EmotiaDatabase
}