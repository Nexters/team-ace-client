package com.nexters.emotia.core.data.chat.datasource

import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.SendChatResponse

interface ChattingRemoteDataSource {
    suspend fun createRoom(username: String): CreateRoomResponse
    suspend fun sendChat(
        roomId: Int,
        message: String
    ): SendChatResponse
}