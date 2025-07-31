package com.nexters.ace.core.data.repository

import com.nexters.ace.core.data.datasource.ChattingRemoteDataSource
import com.nexters.ace.core.data.mapper.toDomain
import com.nexters.ace.domain.ChattingRepository
import com.nexters.ace.domain.entity.ChattingRoom

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
