package com.nexters.emotia.core.data.di

import com.nexters.emotia.core.data.chat.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chat.datasource.ChattingRemoteDataSourceImpl
import com.nexters.emotia.core.data.chat.repository.ChattingRepositoryImpl
import com.nexters.emotia.core.data.config.repository.RemoteConfigRepositoryImpl
import com.nexters.emotia.domain.chat.ChattingRepository
import com.nexters.emotia.domain.config.RemoteConfigRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        // DataSource 주입
        singleOf(::ChattingRemoteDataSourceImpl).bind<ChattingRemoteDataSource>()

        // Repository 주입
        singleOf(::ChattingRepositoryImpl).bind<ChattingRepository>()
        singleOf(::RemoteConfigRepositoryImpl).bind<RemoteConfigRepository>()
    }
