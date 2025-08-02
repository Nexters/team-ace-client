package com.nexters.emotia.core.data.chat

import com.nexters.ace.network.FakeResponse

interface ChattingRemoteDataSource {
    suspend fun createRoom(): FakeResponse
}