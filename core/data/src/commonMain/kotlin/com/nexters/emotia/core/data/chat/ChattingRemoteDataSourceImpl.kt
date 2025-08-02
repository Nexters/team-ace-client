package com.nexters.emotia.core.data.chat

import com.nexters.emotia.network.ApiService
import com.nexters.emotia.network.FakeResponse

class ChattingRemoteDataSourceImpl(
    private val apiService: ApiService
) : ChattingRemoteDataSource {
    
    override suspend fun createRoom(): FakeResponse {
        return apiService.getGeolocationInfo("24.48.0.1")
    }
}