package com.nexters.emotia.network.di

import com.nexters.emotia.network.ApiService
import com.nexters.emotia.network.EmotiaNetwork
import org.koin.dsl.module

val coreNetworkModule = module {
    single { EmotiaNetwork() }
    single { ApiService(get()) }
}
