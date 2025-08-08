package com.nexters.emotia.domain.chat

import com.nexters.emotia.domain.chat.entity.ChatMessage
import com.nexters.emotia.domain.chat.entity.ChattingRoom
import com.nexters.emotia.domain.chat.entity.Fairy

interface ChattingRepository {
    suspend fun createRoom(username: String): Result<ChattingRoom>
    suspend fun sendChat(roomId: Int, message: String): Result<ChatMessage>
    suspend fun getFairies(chatRoomId: String): Result<List<Fairy>>
}