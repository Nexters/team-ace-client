package com.nexters.emotia.core.data.onboarding.di

import com.nexters.emotia.core.data.onboarding.datasource.LoginDataSource
import com.nexters.emotia.core.data.onboarding.datasource.LoginDataSourceImpl
import com.nexters.emotia.core.data.onboarding.repository.LoginRepositoryImpl
import com.nexters.emotia.core.data.onboarding.repository.TokenRepositoryImpl
import com.nexters.emotia.core.domain.onboarding.LoginRepository
import com.nexters.emotia.core.domain.onboarding.repository.TokenRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataOnboardingModule =
    module {
        // DataSource 주입
        singleOf(::LoginDataSourceImpl).bind<LoginDataSource>()

        // Repository 주입
        singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
        singleOf(::TokenRepositoryImpl).bind<TokenRepository>()
    }
