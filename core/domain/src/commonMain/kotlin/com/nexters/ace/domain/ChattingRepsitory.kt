package com.nexters.ace.domain

import com.nexters.ace.domain.entity.ChattingRoom

interface ChattingRepsitory {
    suspend fun createRoom(): Result<ChattingRoom>
}