package com.nexters.emotia.feature.result.di

import com.nexters.emotia.feature.result.ResultViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val resultModule = module {
    viewModel { ResultViewModel(get()) }
}