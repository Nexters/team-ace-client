package com.nexters.ace.core.data.repository

import com.nexters.ace.core.data.mapper.toDomain
import com.nexters.ace.domain.di.ChattingRepsitory
import com.nexters.ace.domain.entity.ChattingRoom
import com.nexters.ace.network.ApiService

class ChattingRepositoryImpl(
    private val apiService: ApiService
) :  ChattingRepsitory{

    override suspend fun createRoom(): Result<ChattingRoom>? {
        return runCatching {
            // 1. API 서비스 호출 (예외 발생 가능)
            val roomDto = apiService.getGeolocationInfo("24.48.0.1")
            // 2. 성공적으로 DTO를 받으면 도메인 모델로 변환
            roomDto.toDomain()
        }.mapCatching { domainModel ->
            domainModel
        }.onSuccess { domainModel ->
            // 성공했을 때의 로깅 또는 추가 처리
            println("Room created successfully: $domainModel")
        }.onFailure { exception ->
            // 실패했을 때의 로깅 또는 추가 처리
            println("Error creating room: ${exception.message}")
        }
    }
}
