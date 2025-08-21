package com.nexters.emotia.core.data.letter.repository

import com.nexters.emotia.core.data.letter.datasource.LetterRemoteDataSource
import com.nexters.emotia.core.data.letter.mapper.toDomain
import com.nexters.emotia.core.domain.letter.entity.Letter
import com.nexters.emotia.core.domain.letter.repository.LetterRepository

class LetterRepositoryImpl(
    private val remoteDataSource: LetterRemoteDataSource,
) : LetterRepository {

    override suspend fun sendLetter(
        chatRoomId: Int,
        fairyId: Int,
        contents: String
    ): Result<Letter> {
        return runCatching {
            val response = remoteDataSource.sendLetter(chatRoomId, fairyId, contents)

            if (!response.success) {
                throw IllegalStateException("API 요청 실패: ${response.error}")
            }

            response.toDomain()
        }.onFailure { exception ->
            println("편지 전송 실패: ${exception.message}")
        }
    }
}