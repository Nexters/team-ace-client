package com.nexters.emotia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
