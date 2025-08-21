package com.nexters.emotia.core.domain.letter.repository

import com.nexters.emotia.core.domain.letter.entity.Letter

interface LetterRepository {
    suspend fun sendLetter(
        chatRoomId: Int,
        fairyId: Int,
        contents: String
    ): Result<Letter>
}