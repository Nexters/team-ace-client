package com.nexters.emotia.feature.onboarding.di

import com.nexters.emotia.feature.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    viewModel {
        OnBoardingViewModel(
            loginRepository = get()
        )
    }
}