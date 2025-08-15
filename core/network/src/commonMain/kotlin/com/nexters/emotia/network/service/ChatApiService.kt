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
        username: String,
        token: String,
    ): CreateRoomResponse {
        return network.post(
            path = "api/v1/chat-rooms",
            body = CreateRoomRequest(username = username),
            token = token
        )
    }

    suspend fun sendChat(
        chatRoomId: String,
        message: String,
        token: String,
    ): SendChatResponse {
        return network.post(
            path = "api/v1/chat-rooms/${chatRoomId}/messages",
            body = SendChatRequest(message = message),
            token = token
        )
    }

    suspend fun getFairies(
        chatRoomId: String,
        token: String,
    ): GetFairiesResponse {
        return network.get(
            path = "api/v1/fairies?chatRoomId=${chatRoomId}",
            token = token
        )
    }
}
