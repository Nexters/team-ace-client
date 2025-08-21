package com.nexters.emotia.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SendLetterRequest(
    val chatRoomId: Int,
    val fairyId: Int,
    val contents: String,
)