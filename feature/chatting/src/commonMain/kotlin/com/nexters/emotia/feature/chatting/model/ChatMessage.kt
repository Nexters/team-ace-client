package com.nexters.emotia.feature.chatting.model

import com.nexters.emotia.core.designsystem.component.BubbleType

data class ChatMessage(
    val text: String,
    val type: BubbleType,
    val timestamp: Long
)
