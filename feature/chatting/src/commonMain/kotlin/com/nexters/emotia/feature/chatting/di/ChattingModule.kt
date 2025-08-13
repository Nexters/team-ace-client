package com.nexters.emotia.feature.chatting.di

import com.nexters.emotia.feature.chatting.ChattingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chattingModule = module {
    viewModel {
        ChattingViewModel(
            chattingRepository = get()
        )
    }
}