package com.nexters.emotia.feature.main.di

import com.nexters.emotia.feature.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule =
    module {
        viewModelOf(::MainViewModel)
    }