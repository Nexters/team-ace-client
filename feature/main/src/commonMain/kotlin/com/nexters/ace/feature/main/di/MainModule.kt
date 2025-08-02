package com.nexters.ace.feature.main.di

import com.nexters.ace.feature.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule =
    module {
        viewModelOf(::MainViewModel)
    }