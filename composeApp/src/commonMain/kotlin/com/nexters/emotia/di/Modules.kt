package com.nexters.emotia.di

import com.nexters.emotia.core.data.di.dataModule
import com.nexters.emotia.network.di.coreNetworkModule
import org.koin.dsl.module

val appModule =
    module {
        includes(
            dataModule,
            coreNetworkModule
        )
    }
