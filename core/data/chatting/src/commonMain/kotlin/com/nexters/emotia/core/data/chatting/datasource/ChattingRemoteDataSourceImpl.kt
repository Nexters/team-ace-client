package com.nexters.emotia.core.data.chatting.datasource

import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.GetFairiesResponse
import com.nexters.emotia.network.dto.response.SendChatResponse
import com.nexters.emotia.network.service.ChatApiService

class ChattingRemoteDataSourceImpl(
    private val apiService: ChatApiService,
) : ChattingRemoteDataSource {

    override suspend fun createRoom(username: String): CreateRoomResponse {
        return apiService.createChatRoom(
            username = username,
        )
    }

    override suspend fun sendChat(
        roomId: Int,
        message: String,
    ): SendChatResponse {
        return apiService.sendChat(
            chatRoomId = roomId.toString(),
            message = message,
        )
    }

    override suspend fun getFairies(chatRoomId: String): GetFairiesResponse {
        return apiService.getFairies(
            chatRoomId = chatRoomId,
            token = EmotiaNetwork.TEST_TOKEN
        )
    }
}
