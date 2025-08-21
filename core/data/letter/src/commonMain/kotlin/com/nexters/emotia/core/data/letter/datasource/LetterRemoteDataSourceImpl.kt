package com.nexters.emotia.core.data.letter.datasource

import com.nexters.emotia.network.dto.response.SendLetterResponse
import com.nexters.emotia.network.service.LetterApiService

class LetterRemoteDataSourceImpl(
    private val apiService: LetterApiService,
) : LetterRemoteDataSource {

    override suspend fun sendLetter(
        chatRoomId: Int,
        fairyId: Int,
        contents: String
    ): SendLetterResponse {
        return apiService.sendLetter(
            chatRoomId = chatRoomId,
            fairyId = fairyId,
            contents = contents
        )
    }
}