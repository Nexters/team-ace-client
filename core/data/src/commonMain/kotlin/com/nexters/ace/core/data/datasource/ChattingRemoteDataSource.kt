package com.nexters.ace.core.data.datasource

import com.nexters.ace.network.FakeResponse

interface ChattingRemoteDataSource {
    suspend fun createRoom(): FakeResponse
}