package com.nexters.emotia.core.platform

interface Platform {
    val name: String
    fun generateDeviceUuid(): String
}

expect fun getPlatform(): Platform