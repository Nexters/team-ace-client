package com.nexters.emotia.core.domain.onboarding.di

import com.nexters.emotia.core.domain.onboarding.manager.TokenManager
import com.nexters.emotia.core.domain.onboarding.usecase.AutoLoginUseCase
import com.nexters.emotia.core.domain.onboarding.usecase.TokenUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainOnboardingModule = module {
    single { TokenManager() }
    singleOf(::AutoLoginUseCase)
    singleOf(::TokenUseCase)
}