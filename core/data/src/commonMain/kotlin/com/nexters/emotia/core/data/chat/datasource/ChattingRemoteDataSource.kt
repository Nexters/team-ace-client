package com.nexters.emotia.core.data.chat.datasource

import com.nexters.emotia.network.dto.response.CreateRoomResponse

interface ChattingRemoteDataSource {
    suspend fun createRoom(username: String): CreateRoomResponse
}