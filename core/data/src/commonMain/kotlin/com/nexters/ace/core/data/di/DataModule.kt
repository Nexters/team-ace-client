package com.nexters.ace.core.data.di

import com.nexters.ace.core.data.datasource.ChattingRemoteDataSource
import com.nexters.ace.core.data.datasource.ChattingRemoteDataSourceImpl
import com.nexters.ace.core.data.repository.ChattingRepositoryImpl
import com.nexters.ace.domain.ChattingRepository
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
