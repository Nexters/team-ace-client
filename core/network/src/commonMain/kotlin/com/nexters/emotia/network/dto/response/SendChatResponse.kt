package com.nexters.emotia.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SendChatResponse(
    val success: Boolean,
    val data: SendChatData?,
    val error: String?
)

@Serializable
data class SendChatData(
    val message: String
)
