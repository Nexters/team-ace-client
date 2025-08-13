package com.nexters.emotia.network

import com.nexters.emotia.core.network.BuildConfig

actual object NetworkConfig {
    actual val baseUrl: String = BuildConfig.BASE_URL
}