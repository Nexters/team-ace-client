package com.nexters.ace.domain

import com.nexters.ace.domain.entity.ChattingRoom

interface ChattingRepository {
    suspend fun createRoom(): Result<ChattingRoom>
}