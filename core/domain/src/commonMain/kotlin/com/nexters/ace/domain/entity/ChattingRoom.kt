package com.nexters.ace.domain.entity

data class ChattingRoom(
    val ipAddress: String?,
    val status: String,
    val country: String,
    val city: String,
    val latitude: Double,
    val longitude: Double
)