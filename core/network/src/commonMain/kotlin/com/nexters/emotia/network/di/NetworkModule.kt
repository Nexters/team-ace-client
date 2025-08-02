package com.nexters.emotia.network.di

import com.nexters.ace.network.AceNetwork
import com.nexters.ace.network.ApiService
import org.koin.dsl.module

val coreNetworkModule = module {
    single { AceNetwork() }
    single { ApiService(get()) }
}
