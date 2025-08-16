package com.nexters.emotia.core.database.di

import com.nexters.emotia.core.database.database.DatabaseFactory
import com.nexters.emotia.core.database.database.EmotiaDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    single<EmotiaDatabase> {
        get<DatabaseFactory>().createDatabase()
    }

    single {
        get<EmotiaDatabase>().chatMessageDao()
    }
}

expect val platformDatabaseModule: Module