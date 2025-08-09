package com.nexters.emotia

interface Platform {
    val name: String
    fun generateDeviceUuid(): String
}

expect fun getPlatform(): Platform
