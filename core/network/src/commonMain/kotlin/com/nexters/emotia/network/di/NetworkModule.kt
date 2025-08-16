package com.nexters.emotia.network.di

import com.nexters.emotia.network.AuthApiService
import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.service.ChatApiService
import org.koin.dsl.module

val coreNetworkModule = module {
    single { EmotiaNetwork(get()) }
    single { ChatApiService(get()) }
    single { AuthApiService(get()) }
}
