package com.nexters.emotia.core.data.chatting.repository

import com.nexters.emotia.core.data.chatting.datasource.ChattingRemoteDataSource
import com.nexters.emotia.core.data.chatting.mapper.toDomain
import com.nexters.emotia.core.database.dao.ChatMessageDao
import com.nexters.emotia.core.database.mapper.toDomainModel
import com.nexters.emotia.core.database.mapper.toEntity
import com.nexters.emotia.core.domain.chatting.entity.ChatMessage
import com.nexters.emotia.core.domain.chatting.entity.ChattingRoom
import com.nexters.emotia.core.domain.chatting.entity.Fairy
import com.nexters.emotia.core.domain.chatting.repository.ChattingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChattingRepositoryImpl(
    private val remoteDataSource: ChattingRemoteDataSource,
    private val localDataSource: ChatMessageDao
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

    // 로컬 room 저장
    override suspend fun saveMessage(message: ChatMessage) {
        localDataSource.insertMessage(message.toEntity())
    }

    override suspend fun saveMessages(messages: List<ChatMessage>) {
        localDataSource.insertMessages(messages.toEntity())
    }

    override fun getSavedMessages(roomId: Int): Flow<List<ChatMessage>> {
        return localDataSource.getMessagesByRoomId(roomId).map { entities ->
            entities.toDomainModel()
        }
    }

    override suspend fun clearSavedMessages(roomId: Int) {
        localDataSource.deleteMessagesByRoomId(roomId)
    }

    override suspend fun getSavedMessageCount(roomId: Int): Int {
        return localDataSource.getMessageCountByRoomId(roomId)
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
