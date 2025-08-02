package com.nexters.emotia.domain.chat

import com.nexters.emotia.domain.chat.entity.ChattingRoom

interface ChattingRepository {
    suspend fun createRoom(): Result<ChattingRoom>
}