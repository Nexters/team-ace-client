package com.nexters.emotia.network.service

import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.dto.request.CreateRoomRequest
import com.nexters.emotia.network.dto.response.CreateRoomResponse

/*
    * TODO : 도메인 (chat, auth, onboarding) 에 따라 apiService를 분리할 지 논의 해보기
 */
class ApiService(
    private val network: EmotiaNetwork,
) {
    suspend fun createChatRoom(
        username: String,
        token: String
    ): CreateRoomResponse {
        return network.post(
            path = "api/v1/chat-rooms",
            body = CreateRoomRequest(username = username),
            token = token
        )
    }
}