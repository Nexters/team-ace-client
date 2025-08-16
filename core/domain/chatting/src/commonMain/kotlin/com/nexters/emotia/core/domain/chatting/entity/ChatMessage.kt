package com.nexters.emotia.core.domain.chatting.entity

data class ChatMessage(
    val id: Long = 0,
    val message: String,
    val senderType: SenderType,
    val timestamp: Long,
    val roomId: String? = null,
)

enum class SenderType {
    MINE, OTHER
}