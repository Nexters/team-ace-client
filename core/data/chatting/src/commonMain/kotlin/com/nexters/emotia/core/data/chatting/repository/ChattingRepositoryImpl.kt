package com.nexters.emotia.core.data.chatting.repository

import com.nexters.emotia.core.data.chatting.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chatting.mapper.toDomain
import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.ChattingRoom
import com.nexters.emotia.core.domain.chatting.repository.ChattingRepository

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

    override suspend fun sendChat(roomId: Int, message: String): Result<ChatMessage> {
        return runCatching {
            val response = remoteDataSource.sendChat(roomId, message)

            if (!response.success) {
                throw IllegalStateException("API 요청 실패: ${response.error}")
            }

            response.toDomain()
        }.onFailure { exception ->
            println("채팅 전송 실패: ${exception.message}")
        }
    }
}
