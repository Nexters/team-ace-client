package com.nexters.emotia.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomResponse(
    val success: Boolean,
    val data: CreateRoomData?,
    val error: String?
)

@Serializable
data class CreateRoomData(
    val chatRoomId: Int,
    val chat: String
)
