package com.nexters.emotia.core.domain.chatting.entity

data class ChatMessage(
    val message: String,
    val senderType: SenderType,
    val timestamp: Long,
)

enum class SenderType {
    MINE, OTHER
}