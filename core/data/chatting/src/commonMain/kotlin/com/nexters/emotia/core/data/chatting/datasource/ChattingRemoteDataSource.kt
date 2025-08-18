package com.nexters.emotia.core.data.chatting.datasource

import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.GetFairiesResponse
import com.nexters.emotia.network.dto.response.SendChatResponse

interface ChattingRemoteDataSource {
    suspend fun createRoom(username: String): CreateRoomResponse
    suspend fun sendChat(
        roomId: String,
        message: String,
    ): SendChatResponse

    suspend fun getFairies(chatRoomId: String): GetFairiesResponse
}
