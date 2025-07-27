package com.nexters.ace.core.data.di

import com.nexters.ace.core.data.repository.ChattingRepositoryImpl
import com.nexters.ace.domain.di.ChattingRepsitory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule =
    module {
        singleOf(::ChattingRepositoryImpl).bind<ChattingRepsitory>()
    }
