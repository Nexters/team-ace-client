package com.nexters.emotia.network.service

import com.nexters.emotia.network.EmotiaNetwork
import com.nexters.emotia.network.dto.request.SendLetterRequest
import com.nexters.emotia.network.dto.response.SendLetterResponse

class LetterApiService(
    private val network: EmotiaNetwork,
) {
    suspend fun sendLetter(
        chatRoomId: Int,
        fairyId: Int,
        contents: String
    ): SendLetterResponse {
        return network.post(
            path = "api/v1/letters",
            body = SendLetterRequest(
                chatRoomId = chatRoomId,
                fairyId = fairyId,
                contents = contents
            )
        )
    }
}