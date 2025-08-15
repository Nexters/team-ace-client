package com.nexters.emotia.core.domain.onboarding.di

import com.nexters.emotia.core.domain.onboarding.usecase.AutoLoginUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainOnboardingModule = module {
    singleOf(::AutoLoginUseCase)
}