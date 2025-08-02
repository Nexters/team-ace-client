package com.nexters.emotia.domain.chat.entity

data class ChattingRoom(
    val ipAddress: String?,
    val status: String,
    val country: String,
    val city: String,
    val latitude: Double,
    val longitude: Double
)