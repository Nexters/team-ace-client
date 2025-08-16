package com.nexters.emotia.di

import com.nexters.emotia.core.data.chatting.di.dataChatModule
import com.nexters.emotia.core.data.onboarding.di.dataOnboardingModule
import com.nexters.emotia.core.domain.onboarding.di.domainOnboardingModule
import com.nexters.emotia.core.platform.di.platformModule
import com.nexters.emotia.feature.main.di.featureModule
import com.nexters.emotia.network.di.coreNetworkModule
import org.koin.dsl.module

val appModule =
    module {
        includes(
            coreNetworkModule,
            platformModule,
            dataChatModule,
            dataOnboardingModule,
            domainOnboardingModule,
            featureModule,
        )
    }
