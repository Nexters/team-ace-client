package com.nexters.emotia.core.data.chatting.datasource

import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.SendChatResponse
import com.nexters.emotia.network.service.ApiService

class ChattingRemoteDataSourceImpl(
    private val apiService: ApiService
) : ChattingRemoteDataSource {

    override suspend fun createRoom(username: String): CreateRoomResponse {
        return apiService.createChatRoom(
            username = username,
            token = EmotiaNetwork.TEST_TOKEN
        )
    }

    override suspend fun sendChat(
        roomId: Int,
        message: String
    ): SendChatResponse {
        return apiService.sendChat(
            chatRoomId = roomId.toString(),
            message = message,
            token = EmotiaNetwork.TEST_TOKEN
        )
    }


}