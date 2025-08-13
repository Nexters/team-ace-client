package com.nexters.emotia.core.data.di

import com.nexters.emotia.core.data.Login.LoginDataSource
import com.nexters.emotia.core.data.Login.LoginDataSourceImpl
import com.nexters.emotia.core.data.Login.Repository.LoginRepositoryImpl
import com.nexters.emotia.core.data.chat.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chat.ChattingRemoteDataSourceImpl
import com.nexters.emotia.core.data.chat.repository.ChattingRepositoryImpl
import com.nexters.emotia.domain.chat.ChattingRepository
import com.nexters.emotia.domain.login.LoginRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        // DataSource 주입
        singleOf(::ChattingRemoteDataSourceImpl).bind<ChattingRemoteDataSource>()

        // Repository 주입
        singleOf(::ChattingRepositoryImpl).bind<ChattingRepository>()

        // DataSource 주입
        singleOf(::LoginDataSourceImpl).bind<LoginDataSource>()

        // Repository 주입
        singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
    }
