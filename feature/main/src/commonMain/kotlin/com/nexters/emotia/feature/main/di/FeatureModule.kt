package com.nexters.emotia.feature.main.di

import com.nexters.emotia.feature.onboarding.di.onBoardingModule
import com.nexters.emotia.feature.chatting.di.chattingModule
import com.nexters.emotia.feature.result.di.resultModule
import org.koin.dsl.module

val featureModule = module {
    includes(
        onBoardingModule,
        chattingModule,
        resultModule
    )
}