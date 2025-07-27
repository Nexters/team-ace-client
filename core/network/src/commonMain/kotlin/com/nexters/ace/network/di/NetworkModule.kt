package com.nexters.ace.network.di

import com.nexters.ace.network.AceNetwork
import org.koin.dsl.module

val coreNetworkModule = module {
    single { AceNetwork() }
}
