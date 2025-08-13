package com.nexters.emotia.network.di

import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.service.ApiService
import org.koin.dsl.module

val coreNetworkModule = module {
    single { EmotiaNetwork() }
    single { ApiService(get()) }
}
