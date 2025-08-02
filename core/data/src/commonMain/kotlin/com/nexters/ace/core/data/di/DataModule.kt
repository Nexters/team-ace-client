package com.nexters.ace.core.data.di

import com.nexters.ace.core.data.chat.ChattingRemoteDataSource
import com.nexters.ace.core.data.chat.ChattingRemoteDataSourceImpl
import com.nexters.ace.core.data.chat.repository.ChattingRepositoryImpl
import com.nexters.ace.domain.chat.ChattingRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        // DataSource 주입
        singleOf(::ChattingRemoteDataSourceImpl).bind<ChattingRemoteDataSource>()

        // Repository 주입
        singleOf(::ChattingRepositoryImpl).bind<ChattingRepository>()
    }
