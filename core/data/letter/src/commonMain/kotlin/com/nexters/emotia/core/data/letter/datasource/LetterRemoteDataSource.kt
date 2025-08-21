package com.nexters.emotia.core.data.letter.datasource

import com.nexters.emotia.network.dto.response.SendLetterResponse

interface LetterRemoteDataSource {
    suspend fun sendLetter(
        chatRoomId: Int,
        fairyId: Int,
        contents: String
    ): SendLetterResponse
}