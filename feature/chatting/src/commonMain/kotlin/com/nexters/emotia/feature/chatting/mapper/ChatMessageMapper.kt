package com.nexters.emotia.feature.chatting.mapper

import com.nexters.emotia.core.designsystem.component.BubbleType
import com.nexters.emotia.core.domain.chatting.entity.SenderType
import com.nexters.emotia.core.domain.chatting.entity.ChatMessage as DomainChatMessage
import com.nexters.emotia.feature.chatting.model.ChatMessage as PresentationChatMessage

// Domain -> Presentation 변환
fun DomainChatMessage.toPresentation(): PresentationChatMessage {
    return PresentationChatMessage(
        text = this.message,
        type = when (this.senderType) {
            SenderType.MINE -> BubbleType.MINE
            SenderType.OTHER -> BubbleType.OTHER
        },
        timestamp = this.timestamp
    )
}

// Presentation -> Domain 변환
fun PresentationChatMessage.toDomain(roomId: String? = null): DomainChatMessage {
    return DomainChatMessage(
        message = this.text,
        senderType = when (this.type) {
            BubbleType.MINE -> SenderType.MINE
            BubbleType.OTHER -> SenderType.OTHER
        },
        timestamp = this.timestamp,
        roomId = roomId
    )
}

// List 변환
fun List<DomainChatMessage>.toPresentation(): List<PresentationChatMessage> {
    return this.map { it.toPresentation() }
}

fun List<PresentationChatMessage>.toDomain(roomId: String? = null): List<DomainChatMessage> {
    return this.map { it.toDomain(roomId) }
}