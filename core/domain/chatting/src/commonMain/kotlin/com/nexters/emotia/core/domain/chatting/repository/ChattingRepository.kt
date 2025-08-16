package com.nexters.emotia.core.domain.chatting.repository

import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.ChattingRoom
import com.nexters.emotia.core.domain.chatting.entity.Fairy
import kotlinx.coroutines.flow.Flow

interface ChattingRepository {
    // API
    suspend fun createRoom(username: String): Result<ChattingRoom>
    suspend fun getFairies(chatRoomId: String): Result<List<Fairy>>
    suspend fun sendChat(roomId: String, message: String): Result<ChatMessage>

    // 채팅 내용 Room에 저장
    suspend fun saveMessage(message: ChatMessage)
    suspend fun saveMessages(messages: List<ChatMessage>)
    fun getSavedMessages(roomId: String): Flow<List<ChatMessage>>
    suspend fun clearSavedMessages(roomId: String)
    suspend fun getSavedMessageCount(roomId: String): Int
}
