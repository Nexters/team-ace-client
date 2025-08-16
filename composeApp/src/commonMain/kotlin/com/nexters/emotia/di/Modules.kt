package com.nexters.emotia.di

import com.nexters.emotia.core.data.chatting.di.dataChatModule
import com.nexters.emotia.core.data.onboarding.di.dataOnboardingModule
import com.nexters.emotia.core.database.di.databaseModule
import com.nexters.emotia.core.database.di.platformDatabaseModule
import com.nexters.emotia.core.domain.onboarding.di.domainOnboardingModule

import com.nexters.emotia.feature.main.di.featureModule
import com.nexters.emotia.network.di.coreNetworkModule
import org.koin.dsl.module

val appModule =
    module {
        includes(
            coreNetworkModule,
            databaseModule,
            platformDatabaseModule,
            dataChatModule,
            dataOnboardingModule,
            domainOnboardingModule,
            featureModule,
        )
    }
