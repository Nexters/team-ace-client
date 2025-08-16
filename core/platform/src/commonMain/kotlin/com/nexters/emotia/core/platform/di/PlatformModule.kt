package com.nexters.emotia.core.platform.di

import com.nexters.emotia.core.platform.Platform
import com.nexters.emotia.core.platform.getPlatform
import org.koin.dsl.module

val platformModule = module {
    single<Platform> { getPlatform() }
}