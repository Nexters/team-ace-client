package com.nexters.emotia.core.data.chat

import com.nexters.emotia.network.FakeResponse

interface ChattingRemoteDataSource {
    suspend fun createRoom(): FakeResponse
}