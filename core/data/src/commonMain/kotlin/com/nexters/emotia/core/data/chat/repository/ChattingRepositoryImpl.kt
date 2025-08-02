package com.nexters.emotia.core.data.chat.repository

import com.nexters.ace.core.data.chat.ChattingRemoteDataSource
import com.nexters.ace.core.data.chat.mapper.toDomain
import com.nexters.ace.domain.chat.ChattingRepository
import com.nexters.ace.domain.chat.entity.ChattingRoom

class ChattingRepositoryImpl(
    private val remoteDataSource: ChattingRemoteDataSource
) : ChattingRepository {

    override suspend fun createRoom(): Result<ChattingRoom> {
        return runCatching {
            val roomDto = remoteDataSource.createRoom()
            roomDto.toDomain()
        }.mapCatching { domainModel ->
            domainModel
        }.onSuccess { domainModel ->
        }.onFailure { exception ->
        }
    }
}
