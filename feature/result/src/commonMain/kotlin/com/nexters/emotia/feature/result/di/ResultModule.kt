package com.nexters.emotia.feature.result.di

import com.nexters.emotia.feature.result.fairy.FairyViewModel
import com.nexters.emotia.feature.result.letter.LetterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val resultModule = module {
    viewModel { FairyViewModel() }
    viewModel { LetterViewModel(get()) }
}