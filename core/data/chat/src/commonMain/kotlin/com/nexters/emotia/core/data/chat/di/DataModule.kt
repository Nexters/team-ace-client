package com.nexters.emotia.core.data.chat.di

import com.nexters.emotia.core.data.chat.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chat.datasource.ChattingRemoteDataSourceImpl
import com.nexters.emotia.core.data.chat.repository.ChattingRepositoryImpl
import com.nexters.emotia.core.domain.chat.repsitory.ChattingRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataChatModule =
    module {
        // DataSource 주입
        singleOf(::ChattingRemoteDataSourceImpl).bind<ChattingRemoteDataSource>()

        // Repository 주입
        singleOf(::ChattingRepositoryImpl).bind<ChattingRepository>()
    }
