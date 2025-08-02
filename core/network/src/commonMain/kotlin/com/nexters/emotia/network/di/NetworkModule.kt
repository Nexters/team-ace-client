package com.nexters.emotia.network.di

import com.nexters.emotia.network.AceNetwork
import com.nexters.emotia.network.ApiService
import org.koin.dsl.module

val coreNetworkModule = module {
    single { AceNetwork() }
    single { ApiService(get()) }
}
