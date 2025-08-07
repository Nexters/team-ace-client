package com.nexters.emotia.core.data.chat.repository

import com.nexters.emotia.core.data.chat.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chat.mapper.toDomain
import com.nexters.emotia.domain.chat.ChattingRepository
import com.nexters.emotia.domain.chat.entity.ChattingRoom

class ChattingRepositoryImpl(
    private val remoteDataSource: ChattingRemoteDataSource
) : ChattingRepository {

    override suspend fun createRoom(username: String): Result<ChattingRoom> {
        return runCatching {
            val response = remoteDataSource.createRoom(username)

            if (!response.success) {
                throw IllegalStateException("API 요청 실패: ${response.error}")
            }

            response.toDomain()
        }.onFailure { exception ->
            println("채팅룸 생성 실패: ${exception.message}")
        }
    }
}
