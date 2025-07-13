package org.nexters.ace

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform