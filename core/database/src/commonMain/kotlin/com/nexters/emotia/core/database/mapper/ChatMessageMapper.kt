package com.nexters.emotia.core.database.mapper

import com.nexters.emotia.core.database.entity.ChatMessageEntity
import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.SenderType

fun ChatMessageEntity.toDomainModel(): ChatMessage {
    return ChatMessage(
        id = this.id,
        message = this.text,
        senderType = when (this.senderType) {
            "MINE" -> SenderType.MINE
            "OTHER" -> SenderType.OTHER
            else -> SenderType.OTHER
        },
        timestamp = this.timestamp,
        roomId = this.roomId
    )
}

fun ChatMessage.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        id = this.id,
        text = this.message,
        senderType = when (this.senderType) {
            SenderType.MINE -> "MINE"
            SenderType.OTHER -> "OTHER"
        },
        timestamp = this.timestamp,
        roomId = this.roomId
    )
}

fun List<ChatMessageEntity>.toDomainModel(): List<ChatMessage> {
    return this.map { it.toDomainModel() }
}

fun List<ChatMessage>.toEntity(): List<ChatMessageEntity> {
    return this.map { it.toEntity() }
}