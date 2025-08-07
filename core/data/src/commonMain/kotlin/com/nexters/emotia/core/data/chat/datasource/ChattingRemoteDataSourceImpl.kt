package com.nexters.emotia.core.data.chat.datasource

import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.dto.response.CreateRoomResponse
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
}