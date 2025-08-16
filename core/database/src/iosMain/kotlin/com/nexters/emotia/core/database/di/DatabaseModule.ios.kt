package com.nexters.emotia.core.database.di

import com.nexters.emotia.core.database.database.DatabaseFactory
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    single<DatabaseFactory> {
        DatabaseFactory()
    }
}