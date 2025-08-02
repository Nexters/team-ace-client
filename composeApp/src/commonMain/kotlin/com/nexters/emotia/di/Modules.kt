package com.nexters.emotia.di

import com.nexters.ace.core.data.di.dataModule
import com.nexters.ace.feature.main.di.mainModule
import com.nexters.ace.network.di.coreNetworkModule
import org.koin.dsl.module

val appModule =
    module {
        includes(
            mainModule,
            dataModule,
            coreNetworkModule
        )
    }
