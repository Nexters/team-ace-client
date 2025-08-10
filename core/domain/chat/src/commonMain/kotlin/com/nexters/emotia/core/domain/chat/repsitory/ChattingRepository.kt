package com.nexters.emotia.core.domain.chat.repsitory

import com.nexters.emotia.core.domain.chat.entity.ChatMessage
import com.nexters.emotia.core.domain.chat.entity.ChattingRoom

interface ChattingRepository {
    suspend fun createRoom(username: String): Result<ChattingRoom>
    suspend fun sendChat(roomId: Int, message: String): Result<ChatMessage>
}