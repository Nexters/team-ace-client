package com.nexters.emotia.core.domain.chatting.repository

import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.ChattingRoom
import com.nexters.emotia.core.domain.chatting.entity.Fairy

interface ChattingRepository {
    suspend fun createRoom(username: String): Result<ChattingRoom>
    suspend fun sendChat(roomId: Int, message: String): Result<ChatMessage>
    suspend fun getFairies(chatRoomId: String): Result<List<Fairy>>
}
