package com.nexters.emotia.network.service

import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.dto.request.CreateRoomRequest
import com.nexters.emotia.network.dto.request.SendChatRequest
import com.nexters.emotia.network.dto.response.CreateRoomResponse
import com.nexters.emotia.network.dto.response.GetFairiesResponse
import com.nexters.emotia.network.dto.response.SendChatResponse

/*
    * TODO : 도메인 (chat, auth, onboarding) 에 따라 apiService를 분리할 지 논의 해보기
 */
class ChatApiService(
    private val network: EmotiaNetwork,
) {
    suspend fun createChatRoom(
        username: String
    ): CreateRoomResponse {
        return network.post(
            path = "api/v1/chat-rooms",
            body = CreateRoomRequest(username = username)
        )
    }

    suspend fun sendChat(
        chatRoomId: String,
        message: String
    ): SendChatResponse {
        return network.post(
            path = "api/v1/chat-rooms/${chatRoomId}/messages",
            body = SendChatRequest(message = message)
        )
    }

    suspend fun getFairies(
        chatRoomId: String,
    ): GetFairiesResponse {
        return network.get(
            path = "api/v1/fairies?chatRoomId=${chatRoomId}",
        )
    }
}
