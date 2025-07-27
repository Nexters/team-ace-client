package com.nexters.ace.domain.di

import com.nexters.ace.domain.entity.ChattingRoom

interface ChattingRepsitory {
    suspend fun createRoom(): Result<ChattingRoom>?
}