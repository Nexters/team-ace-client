package com.nexters.emotia.core.data.chatting.repository

import com.nexters.emotia.core.data.chatting.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chatting.mapper.toDomain
import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.ChattingRoom
import com.nexters.emotia.core.domain.chatting.entity.Fairy
import com.nexters.emotia.core.domain.chatting.repository.ChattingRepository

class ChattingRepositoryImpl(
    private val remoteDataSource: ChattingRemoteDataSource,
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

    override suspend fun sendChat(roomId: String, message: String): Result<ChatMessage> {
        return runCatching {
            val response = remoteDataSource.sendChat(roomId.toInt(), message)

            if (!response.success) {
                throw IllegalStateException("API 요청 실패: ${response.error}")
            }

            response.toDomain()
        }.onFailure { exception ->
            println("채팅 전송 실패: ${exception.message}")
        }
    }

    override suspend fun getFairies(chatRoomId: String): Result<List<Fairy>> {
        return runCatching {
            val response = remoteDataSource.getFairies(chatRoomId)

            if (!response.success) {
                throw IllegalStateException("API 요청 실패: ${response.error}")
            }

            response.toDomain()
        }.onFailure { exception ->
            println("요정 정보 조회 실패: ${exception.message}")
        }
    }
}
