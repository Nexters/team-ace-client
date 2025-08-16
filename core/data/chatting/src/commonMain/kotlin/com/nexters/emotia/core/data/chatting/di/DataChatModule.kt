package com.nexters.emotia.core.data.chatting.di

import com.nexters.emotia.core.data.chatting.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chatting.datasource.ChattingRemoteDataSourceImpl
import com.nexters.emotia.core.data.chatting.repository.ChattingRepositoryImpl
import com.nexters.emotia.core.domain.chatting.repository.ChattingRepository
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
