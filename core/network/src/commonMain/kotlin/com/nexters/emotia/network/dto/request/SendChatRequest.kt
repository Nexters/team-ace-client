package com.nexters.emotia.network.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SendChatRequest(
    val message: String
)